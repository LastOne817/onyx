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
package edu.snu.onyx.runtime.common.eventhandler;

import edu.snu.onyx.common.Pair;
import edu.snu.onyx.common.eventhandler.CompilerEvent;
import edu.snu.onyx.runtime.common.plan.physical.PhysicalPlan;
import edu.snu.onyx.runtime.common.plan.physical.TaskGroup;

/**
 * An event for updating the physical plan in the scheduler.
 */
public final class UpdatePhysicalPlanEvent implements CompilerEvent {
  private final PhysicalPlan newPhysicalPlan;
  private final Pair<String, TaskGroup> taskInfo;

  /**
   * Constructor.
   * @param newPhysicalPlan the newly optimized physical plan.
   * @param taskInfo information of the task at which this optimization occurs: its name and its taskGroup.
   */
  UpdatePhysicalPlanEvent(final PhysicalPlan newPhysicalPlan,
                          final Pair<String, TaskGroup> taskInfo) {
    this.newPhysicalPlan = newPhysicalPlan;
    this.taskInfo = taskInfo;
  }

  /**
   * @return the updated, newly optimized physical plan.
   */
  public PhysicalPlan getNewPhysicalPlan() {
    return this.newPhysicalPlan;
  }

  /**
   * @return the information of the task at which this optimization occurs: its name and its taskGroup.
   */
  public Pair<String, TaskGroup> getTaskInfo() {
    return this.taskInfo;
  }
}
