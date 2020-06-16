/*
 * Copyright 2018-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.flysium.io.sample.groovy

/**
 * Class Test fot Groovy.
 *
 * @author Sven Augustus
 */
class Song {
    def name
    def artist
    def genre

    def getGenre() {
        genre?.toUpperCase()
    }
}

class GroovyClassTest {

    static void main(args) {
        // 类初始化
        def sng = new Song(name: "Le Freak", artist: "Chic", genre: "Disco")
        def sng2 = new Song(name: "Kung Fu Fighting", genre: "Disco")
        def sng3 = new Song()
        sng3.name = "Funkytown"
        sng3.artist = "Lipps Inc."
        sng3.setGenre("Disco")
        assert sng3.getArtist() == "Lipps Inc."

        sng3.setGenre "Disco"
        assert sng3.genre == "DISCO"
        // 在 Groovy 中，对于接受参数的方法，可以省略括号 — 在某些方面，这样做会让代码更容易阅读。

        println sng3

        // 可恶的 null
//        println sng2.artist.toUpperCase
        //  Groovy 通过 ? 操作符提供了一个安全网 — 在方法调用前面添加一个 ? 就相当于在调用前面放了一个条件，可以防止在 null 对象上调用方法。
        println sng2.artist?.toUpperCase

    }

}

