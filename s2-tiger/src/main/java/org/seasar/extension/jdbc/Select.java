/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.extension.jdbc;

import java.util.List;

import org.seasar.extension.jdbc.exception.OrderByNotFoundRuntimeException;
import org.seasar.extension.jdbc.exception.SNonUniqueResultException;

/**
 * 検索のベースとなるインターフェースです。
 * 
 * @author higa
 * @param <T>
 *            戻り値のベースの型です。
 * @param <S>
 *            <code>Select</code>のサブタイプです。
 */
public interface Select<T, S extends Select<T, S>> extends Query<Select<T, S>> {

    /**
     * 最大行数を設定します。
     * 
     * @param maxRows
     *            最大行数
     * @return このインスタンス自身
     */
    S maxRows(int maxRows);

    /**
     * フェッチ数を設定します。
     * 
     * @param fetchSize
     *            フェッチ数
     * @return このインスタンス自身
     */
    S fetchSize(int fetchSize);

    /**
     * リミットを設定します。
     * 
     * @param limit
     *            リミット
     * @return このインスタンス自身
     */
    S limit(int limit);

    /**
     * オフセットを設定します。
     * 
     * @param offset
     *            オフセット
     * @return このインスタンス自身
     */
    S offset(int offset);

    /**
     * 検索してベースオブジェクトのリストを返します。
     * 
     * @return
     * <p>
     * ベースオブジェクトのリスト。
     * </p>
     * <p>
     * 1件も対象がないときはnullではなく空のリストを返します。
     * </p>
     * @throws OrderByNotFoundRuntimeException
     *             ページング処理で<code>order by</code>が見つからない場合
     */
    List<T> getResultList() throws OrderByNotFoundRuntimeException;

    /**
     * 検索してベースオブジェクトを返します。
     * 
     * @return
     * <p>
     * ベースオブジェクト。
     * </p>
     * <p>
     * 1件も対象がないときはnullを返します。
     * </p>
     * @throws SNonUniqueResultException
     *             検索結果がユニークでない場合。
     */
    T getSingleResult() throws SNonUniqueResultException;
}