/*
 * MIT License
 *
 * Copyright (c) 2020 SvenAugustus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.flysium.photon

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

