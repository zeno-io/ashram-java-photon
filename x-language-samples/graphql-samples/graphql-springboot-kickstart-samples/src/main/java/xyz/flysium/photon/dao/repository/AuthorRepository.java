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

package xyz.flysium.photon.dao.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import xyz.flysium.photon.dao.entity.Author;

/**
 * @author zeno (Sven Augustus)
 * @version 1.0
 */
@Component
@Mapper
public interface AuthorRepository extends BaseMapper<Author> {

    //  private static final List<Author> authors = Arrays.asList(
    //      AuthorBuilder.anAuthor().id(1).firstName("Joanne").lastName("Rowling").build(),
    //      AuthorBuilder.anAuthor().id(2).firstName("Herman").lastName("Melville").build(),
    //      AuthorBuilder.anAuthor().id(3).firstName("Anne").lastName("Rice").build()
    //  );

    @Select("select  ID, FIRST_NAME FIRSTNAME, LAST_NAME AS LASTNAME from author where id = #{authorId}")
    public Author findById(@Param("authorId") int authorId); /*{
    return authors
        .stream()
        .filter(author -> author.getId() == authorId)
        .findFirst()
        .orElse(null);
  }*/

    @Select("<script>" + "SELECT ID, FIRST_NAME FIRSTNAME, LAST_NAME AS LASTNAME from author WHERE id IN "
        + "<foreach item='key' index='index' collection='keys'      open='(' separator=',' close=')'>" + "#{key}"
        + "</foreach>" + "</script>")
    List<Author> findAllById(@Param("keys") List<Integer> keys);

}
