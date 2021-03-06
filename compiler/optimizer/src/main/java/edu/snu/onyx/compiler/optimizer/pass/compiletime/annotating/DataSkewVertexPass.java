/*
 * Copyright (C) 2017 Seoul National University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.snu.onyx.compiler.optimizer.pass.compiletime.annotating;

import edu.snu.onyx.common.dag.DAG;
import edu.snu.onyx.common.ir.edge.IREdge;
import edu.snu.onyx.common.ir.vertex.IRVertex;
import edu.snu.onyx.common.ir.vertex.MetricCollectionBarrierVertex;
import edu.snu.onyx.common.ir.executionproperty.ExecutionProperty;
import edu.snu.onyx.common.ir.vertex.executionproperty.DynamicOptimizationProperty;

/**
 * Pass to annotate the DAG for a job to perform data skew.
 * It specifies which optimization to perform on the MetricCollectionBarrierVertex.
 */
public final class DataSkewVertexPass extends AnnotatingPass {
  /**
   * Default constructor.
   */
  public DataSkewVertexPass() {
    super(ExecutionProperty.Key.DynamicOptimizationType);
  }

  @Override
  public DAG<IRVertex, IREdge> apply(final DAG<IRVertex, IREdge> dag) {
    dag.topologicalDo(v -> {
      // we only care about metric collection barrier vertices.
      if (v instanceof MetricCollectionBarrierVertex) {
        v.setProperty(DynamicOptimizationProperty.of(DynamicOptimizationProperty.Value.DataSkewRuntimePass));
      }
    });
    return dag;
  }
}
