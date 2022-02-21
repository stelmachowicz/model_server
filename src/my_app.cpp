#include <iostream>

#include <openvino/openvino.hpp>

using namespace ov;

int main(int argc, char** argv) {
    std::string model_name = argv[1];
    std::string device = argv[2];
    std::cout << "Working with: " << model_name << std::endl;

    auto core = ov::Core();
    auto model = core.read_model(model_name);
    model->get_parameters()[0]->set_layout("N...");
    ov::set_batch(model, ov::Dimension(8));
    auto cmodel = core.compile_model(model, device);

    auto ir = cmodel.create_infer_request();
    auto input_name = model->input().get_any_name();
    auto output_name = model->output().get_any_name();
    auto tensor = ov::Tensor(ov::element::f32, ov::Shape{8, 3, 224, 224});
    ir.set_tensor(input_name, tensor);
    auto out = ir.get_tensor(output_name);

    std::cout << "Hi" << ((float*)out.data())[0] <<  std::endl;
    return 0;
}
