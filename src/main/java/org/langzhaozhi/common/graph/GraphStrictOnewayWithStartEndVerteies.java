package org.langzhaozhi.common.graph;

/**
 * <带起始结束定点的严格单向图>：有且只有一个开始顶点，有且只有一个结束顶点
 *
 * @param <A> 绑定于图顶点上的数据对象或附件对象(Attachment),具体由应用作出解释,不做任何限制性规定
 * @param <V> 顶点Vertex<A>
 */
public interface GraphStrictOnewayWithStartEndVerteies<A, V extends Vertex<A>> extends GraphStrictOneway {
    public V getStartVertex();

    public V getEndVertex();
}
