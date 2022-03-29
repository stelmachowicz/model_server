#include <openvino/openvino.hpp>

int main() {
    ov::Core core;
    std::shared_ptr<ov::Model> model = core.read_model("/ovms/src/test/dummy/1/dummy.xml");
    model->reshape({
        {"b", ov::PartialShape{1, -1}}
    });

    ov::CompiledModel compiledModel = core.compile_model(model, "CPU");

    ov::InferRequest ireq = compiledModel.create_infer_request();

    ov::Tensor tensor(ov::element::f32, ov::Shape{1, 2});
    float* d = (float*)tensor.data();
    d[0] = 3.1;
    d[1] = 3.8;

    ireq.set_tensor("b", tensor);
    ov::Tensor my_out(ov::element::f32, ov::Shape{1, 555});
    std::cout << my_out.get_shape() << std::endl;
    std::cout << (void*)my_out.data() << std::endl;
    ireq.set_tensor("a", my_out);

    ireq.infer();

    ov::Tensor output = ireq.get_tensor("a");
    std::cout << output.get_shape() << std::endl;
    std::cout << (void*)output.data() << std::endl;
    d = (float*)output.data();
    std::cout << d[0] << " " << d[1] << std::endl;

    std::cout << my_out.get_shape() << std::endl;
    std::cout << (void*)my_out.data() << std::endl;
    std::cout << my_out.get_size() << std::endl;
    std::cout << my_out.get_byte_size() << std::endl;


    ov::Tensor tensor2(ov::element::f32, ov::Shape{1, 3});
    d = (float*)tensor2.data();
    d[0] = 3.1;
    d[1] = 3.8;
    d[2] = 4.8;

    ireq.set_tensor("b", tensor2);

    ireq.infer();

    output = ireq.get_tensor("a");
    std::cout << output.get_shape() << std::endl;
    std::cout << (void*)output.data() << std::endl;
    d = (float*)output.data();
    std::cout << d[0] << " " << d[1] << " " << d[2] << std::endl;

    return 0;
}
