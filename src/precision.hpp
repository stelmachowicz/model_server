//*****************************************************************************
// Copyright 2021 Intel Corporation
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

#include <string>

#include <openvino/openvino.hpp>
// TODO remove from here
#pragma GCC diagnostic push
#pragma GCC diagnostic ignored "-Wall"
#include "tensorflow_serving/apis/prediction_service.grpc.pb.h"
#pragma GCC diagnostic pop

namespace ovms {

using KFSDataType = std::string;
using TFSDataType = tensorflow::DataType;

enum class Precision {
    BF16,
    FP64,
    FP32,
    FP16,
    I64,
    I32,
    I16,
    I8,
    I4,
    U64,
    U32,
    U16,
    U8,
    U4,
    U1,
    BOOL,
    CUSTOM,
    UNDEFINED,
    DYNAMIC,
    MIXED,
    Q78,
    BIN
};

std::string toString(Precision precision);

Precision fromString(const std::string& s);

Precision KFSPrecisionToOvmsPrecision(const KFSDataType& s);
Precision TFSPrecisionToOvmsPrecision(const TFSDataType& s);

size_t KFSDataTypeSize(const KFSDataType& datatype);

KFSDataType ovmsPrecisionToKFSPrecision(Precision precision);

ov::element::Type_t ovmsPrecisionToIE2Precision(Precision precision);

Precision ovElementTypeToOvmsPrecision(ov::element::Type_t type);
}  // namespace ovms
