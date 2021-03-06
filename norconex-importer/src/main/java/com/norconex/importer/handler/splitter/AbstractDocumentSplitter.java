/* Copyright 2014-2015 Norconex Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.norconex.importer.handler.splitter;

import java.io.OutputStream;
import java.util.List;

import com.norconex.commons.lang.config.IXMLConfigurable;
import com.norconex.commons.lang.io.CachedStreamFactory;
import com.norconex.importer.doc.ImporterDocument;
import com.norconex.importer.handler.AbstractImporterHandler;
import com.norconex.importer.handler.ImporterHandlerException;

/**
 * <p>Base class for splitters.</p>
 * 
 * <p>Subclasses inherit this {@link IXMLConfigurable} configuration:</p>
 * <pre>
 *  &lt;restrictTo caseSensitive="[false|true]"
 *          field="(name of header/metadata field name to match)"&gt;
 *      (regular expression of value to match)
 *  &lt;/restrictTo&gt;
 *  &lt;!-- multiple "restrictTo" tags allowed (only one needs to match) --&gt;
 * </pre>
 * @author Pascal Essiembre
 * @since 2.0.0
 */
public abstract class AbstractDocumentSplitter extends AbstractImporterHandler
            implements IDocumentSplitter {

    public AbstractDocumentSplitter() {
        super("splitter");
    }

    @Override
    public final List<ImporterDocument> splitDocument(
            SplittableDocument doc, 
            OutputStream docOutput,
            CachedStreamFactory streamFactory, boolean parsed) 
                    throws ImporterHandlerException {
        
        if (!isApplicable(doc.getReference(), doc.getMetadata(), parsed)) {
            return null;
        }
        return splitApplicableDocument(
                doc, docOutput, streamFactory, parsed);
    }

    protected abstract List<ImporterDocument> splitApplicableDocument(
            SplittableDocument doc, OutputStream output, 
            CachedStreamFactory streamFactory, boolean parsed) 
                    throws ImporterHandlerException;
}