//*****************************************************************************
// Copyright 2020 Intel Corporation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//*****************************************************************************
#pragma once

#include <atomic>
#include <condition_variable>
#include <future>
#include <memory>
#include <mutex>
#include <queue>
#include <thread>
#include <vector>
#include <unordered_map>
#include <string>

#include <openvino/openvino.hpp>
#include <spdlog/spdlog.h>

#include "queue.hpp"
#include "profiler.hpp"

namespace ovms {

class OVInferRequestsQueue : public Queue<ov::InferRequest> {
public:
    OVInferRequestsQueue(ov::CompiledModel& compiledModel, int streamsLength) :
        Queue(streamsLength) {
        for (int i = 0; i < streamsLength; ++i) {
            streams[i] = i;
            inferRequests.push_back(compiledModel.create_infer_request());
        }
    }
};

using PreallocatedTensorMap = std::unordered_map<std::string, ov::Tensor>;

class OVTensorQueue : public Queue<PreallocatedTensorMap> {
public:
    OVTensorQueue(ov::Model& model, int streamsLength) :
        Queue(streamsLength) {
        for (int i = 0; i < streamsLength; ++i) {
            streams[i] = i;
            PreallocatedTensorMap map;
            for (auto& output : model.outputs()) {
                map[output.get_any_name()] = ov::Tensor(output.get_element_type(), output.get_shape());
            }
            inferRequests.emplace_back(std::move(map));
        }
    }
};

class SavedTensor {
    OVTensorQueue& queue;
    int streamID;
    //ov::Tensor tensor;
public:
    SavedTensor(OVTensorQueue& queue, int streamID) :
        queue(queue),
        streamID(streamID)
        //tensor(tensor)
    {
    }
    SavedTensor(const SavedTensor&) = delete;
    ~SavedTensor() {
        OVMS_PROFILE_SCOPE("Bottleneck Free");
        queue.returnStream(streamID);
        std::cout << "Freed Stream ID " << streamID << std::endl;
    }
};

}  // namespace ovms
