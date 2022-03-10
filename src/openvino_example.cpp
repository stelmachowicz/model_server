#include <map>
#include <string>
#include <memory>
#include <utility>
#include <iostream>
#include <sstream>
#include <openvino/openvino.hpp>

using plugin_config_t = std::map<std::string, ov::Any>;

int main() {
    std::string modelFile = "/ovms/src/test/dummy/1/dummy.xml";
    std::string targetDevice = "CPU";
    plugin_config_t pluginConfig;
    //pluginConfig["GPU_THROUGHPUT_STREAMS"] = "GPU_THROUGHPUT_AUTO";
    pluginConfig["CPU_THROUGHPUT_STREAMS"] = "CPU_THROUGHPUT_AUTO";
    auto ieCore = std::make_unique<ov::Core>();
    auto model = ieCore->read_model(modelFile);
    std::map<std::string, ov::PartialShape> modelShapes;
    ov::Shape shape = ov::Shape{1,3*224*224};
    modelShapes["b"] = shape;
    model->reshape(modelShapes);
    auto compiledModel = std::make_shared<ov::CompiledModel>(ieCore->compile_model(model, targetDevice, pluginConfig));
    auto inferRequest = compiledModel->create_infer_request();
    size_t sizea = 1;
    for (auto d : shape) {
        sizea *= d;
    }
    size_t size = sizea * sizeof(float);
    std::string stringBuffer(size, char(0));
    auto tensor = ov::Tensor(ov::element::Type_t::f32, shape, (void*)(stringBuffer.c_str()));
    std::string inputName = "b";
    std::string outputName = "a";
    for (const ov::Output<ov::Node>& input : model->inputs()) {
            //std::string name = input.get_any_name();
            //ov::PartialShape shape = input.get_partial_shape();
            inferRequest.set_tensor("b", tensor);
    }
        inferRequest.start_async();
        /*inferRequest.set_callback([this, &notifyEndQueue, &inferRequest, &node](std::exception_ptr exception_ptr) {
            this->timer->stop("inference");
            inferRequest.set_callback([](std::exception_ptr exception_ptr) {});  // reset callback on infer request
        });*/
        inferRequest.wait();
        auto tensorOutput = inferRequest.get_tensor(outputName);
        const char* data = (char*)tensorOutput.data();
        std::stringstream ss;
        for (int i = 0; i < sizea; ++i) {
            ss << *(((float*)(tensor.data())) + 1);
        }
        std::cout << ss.str() << std::endl;
        for (int i = 0; i < sizea; ++i) {
                std::cout << *(((float*)(data)) + 1);
        }
        std::cout << std::endl;
    return 0;
}
