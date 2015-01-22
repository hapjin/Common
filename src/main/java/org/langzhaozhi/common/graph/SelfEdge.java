package org.langzhaozhi.common.graph;

/**
 * 自指边，在一些特殊的图中允许一个顶点有指向自己的边，例如范畴论表示中任何对象x都存在一个恒等映射就是指向自己的单位态射i : x -> x
 * 先暂时定义在这里，看以后有没有用处
 *
 * @see Edge
 */
public interface SelfEdge {
}
