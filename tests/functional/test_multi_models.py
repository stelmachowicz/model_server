#
# Copyright (c) 2018-2019 Intel Corporation
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
import pytest
import numpy as np
from constants import PREDICTION_SERVICE, MODEL_SERVICE, ERROR_SHAPE
from model.models_information import Resnet, ResnetBS4, ResnetBS8, ResnetS3, ResnetGS
from utils.grpc import infer, get_model_metadata, \
    model_metadata_response, get_model_status
from utils.models_utils import ModelVersionState, ErrorCode, \
    ERROR_MESSAGE  # noqa
from utils.rest import infer_rest, \
    get_model_metadata_response_rest, get_model_status_response_rest


class TestMultiModelInference:

    def test_run_inference(self, resnet_multiple_batch_sizes,
                           start_server_multi_model,
                           create_grpc_channel):

        _, ports = start_server_multi_model
        print("Downloaded model files:", resnet_multiple_batch_sizes)

        # Connect to grpc service
        stub = create_grpc_channel('localhost:{}'.format(ports["grpc_port"]),
                                   PREDICTION_SERVICE)

        for model in [Resnet, ResnetBS4, ResnetBS8]:
            input_data = np.ones(model.input_shape, model.dtype)
            print("Starting inference using {} model".format(model.name))
            output = infer(input_data, input_tensor=model.input_name,
                           grpc_stub=stub,
                           model_spec_name=model.name,
                           model_spec_version=None,
                           output_tensors=[model.output_name])
            print("output shape: {} for model {} ".format(output[model.output_name].shape, model.name))
            assert_msg = "{} for model {}".format(ERROR_SHAPE, model.name)
            assert output[model.output_name].shape == model.output_shape, assert_msg

    """
    No s3,gs support yet

        output = infer(img, input_tensor=in_name, grpc_stub=stub,
                       model_spec_name='resnet_s3',
                       model_spec_version=None,
                       output_tensors=[out_name])
        print("output shape", output[out_name].shape)
        assert output[out_name].shape == (1, 1001), ERROR_SHAPE

        in_name = 'input'
        out_name = 'resnet_v1_50/predictions/Reshape_1'

        img = np.ones((1, 3, 224, 224))
        in_name = 'data'
        out_name = 'prob'
        output = infer(img, input_tensor=in_name, grpc_stub=stub,
                       model_spec_name='resnet_gs',
                       model_spec_version=None,
                       output_tensors=[out_name])
        print("output shape", output[out_name].shape)
        assert output[out_name].shape == (1, 1000), ERROR_SHAPE
    """

    def test_get_model_metadata(self, resnet_multiple_batch_sizes,
                                start_server_multi_model,
                                create_grpc_channel):
        _, ports = start_server_multi_model
        print("Downloaded model files:", resnet_multiple_batch_sizes)

        # Connect to grpc service
        stub = create_grpc_channel('localhost:{}'.format(ports["grpc_port"]),
                                   PREDICTION_SERVICE)

        for model in [Resnet, ResnetBS4, ResnetBS8]:
            print("Getting info about {} model".format(model.name))
            expected_input_metadata = {model.input_name: {'dtype': 1, 'shape': list(model.input_shape)}}
            expected_output_metadata = {model.output_name: {'dtype': 1, 'shape': list(model.output_shape)}}
            request = get_model_metadata(model_name=model.name)
            response = stub.GetModelMetadata(request, 10)
            input_metadata, output_metadata = model_metadata_response(response=response)

            print(output_metadata)
            assert response.model_spec.name == model.name
            assert expected_input_metadata == input_metadata
            assert expected_output_metadata == output_metadata

    @pytest.mark.skip(reason="not implemented yet")
    def test_get_model_status(self, resnet_multiple_batch_sizes,
                              start_server_multi_model,
                              create_grpc_channel):

        _, ports = start_server_multi_model
        print("Downloaded model files:", resnet_multiple_batch_sizes)

        stub = create_grpc_channel('localhost:{}'.format(ports["grpc_port"]),
                                   MODEL_SERVICE)

        for model in [Resnet, ResnetBS4, ResnetBS8]:
            request = get_model_status(model_name=model.name, version=1)
            response = stub.GetModelStatus(request, 10)
            versions_statuses = response.model_version_status
            version_status = versions_statuses[0]
            assert version_status.version == 1
            assert version_status.state == ModelVersionState.AVAILABLE
            assert version_status.status.error_code == ErrorCode.OK
            assert version_status.status.error_message == ERROR_MESSAGE[
                ModelVersionState.AVAILABLE][ErrorCode.OK]

    @pytest.mark.skip(reason="not implemented yet")
    def test_run_inference_rest(self, resnet_multiple_batch_sizes,
                                start_server_multi_model):

        _, ports = start_server_multi_model
        print("Downloaded model files:", resnet_multiple_batch_sizes)

        for model in [Resnet, ResnetBS4, ResnetBS8, ResnetS3, ResnetGS]:
            input_data = np.ones(model.input_shape, model.dtype)
            print("Starting inference using {} model".format(model.name))

            rest_url = 'http://localhost:{}/v1/models/{}:predict'.format(ports["rest_port"], model.name)
            output = infer_rest(input_data, input_tensor=model.input_name, rest_url=rest_url,
                                output_tensors=[model.output_name],
                                request_format=model.rest_request_format)
            print("output shape", output[model.output_name].shape)
            assert output[model.output_name].shape == model.output_shape, ERROR_SHAPE

    @pytest.mark.skip(reason="not implemented yet")
    def test_get_model_metadata_rest(self, resnet_multiple_batch_sizes,
                                     start_server_multi_model):

        _, ports = start_server_multi_model
        print("Downloaded model files:", resnet_multiple_batch_sizes)

        for model in [Resnet, ResnetBS4]:
            print("Getting info about {} model".format(model.name))
            expected_input_metadata = {model.input_name: {'dtype': 1, 'shape': list(model.input_shape)}}
            expected_output_metadata = {model.output_name: {'dtype': 1, 'shape': list(model.output_shape)}}
            rest_url = 'http://localhost:{}/v1/models/{}/metadata'.format(ports["rest_port"], model.name)
            response = get_model_metadata_response_rest(rest_url)
            input_metadata, output_metadata = model_metadata_response(response=response)

            print(output_metadata)
            assert response.model_spec.name == model.name
            assert expected_input_metadata == input_metadata
            assert expected_output_metadata == output_metadata

    @pytest.mark.skip(reason="not implemented yet")
    def test_get_model_status_rest(self, resnet_multiple_batch_sizes,
                                   start_server_multi_model):

        _, ports = start_server_multi_model
        print("Downloaded model files:", resnet_multiple_batch_sizes)

        for model in [Resnet, ResnetBS4]:
            rest_url = 'http://localhost:{}/v1/models/{}'.format(ports["rest_port"], model.name)
            response = get_model_status_response_rest(rest_url)
            versions_statuses = response.model_version_status
            version_status = versions_statuses[0]
            assert version_status.version == 1
            assert version_status.state == ModelVersionState.AVAILABLE
            assert version_status.status.error_code == ErrorCode.OK
            assert version_status.status.error_message == ERROR_MESSAGE[
                ModelVersionState.AVAILABLE][ErrorCode.OK]
