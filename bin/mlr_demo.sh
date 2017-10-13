#!/usr/bin/env bash

./bin/run.sh \
-job_id MLR \
-user_main edu.snu.vortex.examples.beam.MultinomialLogisticRegression \
-optimization_policy edu.snu.vortex.compiler.optimizer.policy.TransientResourcePolicy \
-user_args "`pwd`/src/main/resources/sample_input_mlr 100 5 3"