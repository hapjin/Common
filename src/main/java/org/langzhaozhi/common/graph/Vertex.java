package org.langzhaozhi.common.graph;

/**
 * 图顶点，简称顶点，有时也称图节点或节点
 *
 * @param <A> 绑定于图顶点上的数据对象或附件对象(Attachment),具体由应用作出解释,不做任何限制性规定
 */
public interface Vertex<A> {
    /**
     * 与该顶点绑定的数据对象,具体由应用作出解释
     * @return 数据对象
     */
    public A getAttachment();
}
