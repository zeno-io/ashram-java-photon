/*
 * Apache License 2.0
 *
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.photon.jvm.c040_runtime_data_area;

import java.util.ArrayList;
import java.util.List;

public class T14_InvokeInterface {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("hello");

        ArrayList<String> list2 = new ArrayList<>();
        list2.add("hello2");
    }
}
