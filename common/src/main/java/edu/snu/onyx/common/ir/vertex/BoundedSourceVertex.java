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
package edu.snu.onyx.common.ir.vertex;

import edu.snu.onyx.common.ir.Reader;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SourceVertex implementation for BoundedSource.
 * @param <O> output type.
 */
public final class BoundedSourceVertex<O> extends SourceVertex<O> {
  private static final Logger LOG = LoggerFactory.getLogger(BoundedSourceVertex.class.getName());
  private final Source<O> source;

  /**
   * Constructor of BoundedSourceVertex.
   * @param source BoundedSource to read from.
   */
  public BoundedSourceVertex(final Source<O> source) {
    this.source = source;
  }

  @Override
  public BoundedSourceVertex getClone() {
    final BoundedSourceVertex that = new BoundedSourceVertex<>(this.source);
    this.copyExecutionPropertiesTo(that);
    return that;
  }

  @Override
  public List<Reader<O>> getReaders(final int desiredNumOfSplits) throws Exception {
    final List<Reader<O>> readers = new ArrayList<>();
    LOG.info("estimate: {}", source.getEstimatedSizeBytes());
    LOG.info("desired: {}", desiredNumOfSplits);
    source.split(source.getEstimatedSizeBytes() / desiredNumOfSplits).forEach(boundedSource ->
        readers.add(new BoundedSourceReader<>(boundedSource)));
    return readers;
  }

  @Override
  public String propertiesToJSON() {
    final StringBuilder sb = new StringBuilder();
    sb.append("{");
    sb.append(irVertexPropertiesToString());
    sb.append(", \"source\": \"");
    sb.append(source);
    sb.append("\"}");
    return sb.toString();
  }

  /**
   * BoundedSourceReader class.
   * @param <T> type.
   */
  public class BoundedSourceReader<T> implements Reader<T> {
    private final Source<T> boundedSource;

    /**
     * Constructor of the BoundedSourceReader.
     * @param boundedSource the BoundedSource.
     */
    BoundedSourceReader(final Source<T> boundedSource) {
      this.boundedSource = boundedSource;
    }

    @Override
    public final Iterable<T> read() throws Exception {
      final ArrayList<T> elements = new ArrayList<>();
      try (Source.Reader<T> reader = boundedSource.createReader()) {
        for (boolean available = reader.start(); available; available = reader.advance()) {
          elements.add(reader.getCurrent());
        }
      }
      return elements;
    }
  }
}
