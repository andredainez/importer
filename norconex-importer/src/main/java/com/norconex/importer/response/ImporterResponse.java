/* Copyright 2014 Norconex Inc.
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
package com.norconex.importer.response;

import java.util.ArrayList;
import java.util.List;

import com.norconex.importer.doc.ImporterDocument;

/**
 * @author Pascal Essiembre
 * @since 2.0.0
 */
public class ImporterResponse {

    public static final ImporterResponse[] EMPTY_RESPONSES = 
            new ImporterResponse[] {};
    
    private final String reference;
    private ImporterStatus status;
    private final ImporterDocument doc;
    private final List<ImporterResponse> nestedResponses = new ArrayList<>();
    private ImporterResponse parentResponse;
    
    public ImporterResponse(String reference, ImporterStatus status) {
        this.reference = reference;
        this.status = status;
        this.doc = null;
    }
    public ImporterResponse(ImporterDocument doc) {
        this.reference = doc.getReference();
        this.doc = doc;
        this.status = new ImporterStatus();
    }

    public ImporterDocument getDocument() {
        return doc;
    }

    public ImporterStatus getImporterStatus() {
        return status;
    }
    public void setImporterStatus(ImporterStatus status) {
        this.status = status;
    }
    
    public boolean isSuccess() {
        return status != null && status.isSuccess();
    }

    public String getReference() {
        return reference;
    }
  
    public ImporterResponse getParentResponse() {
        return parentResponse;
    }
    
    
    public void addNestedResponse(ImporterResponse response) {
        response.setParentResponse(this);
        nestedResponses.add(response);
    }
    public void removeNestedResponse(String reference) {
        ImporterResponse response = null;
        for (ImporterResponse nestedResponse : nestedResponses) {
            if (nestedResponse.getReference().equals(reference)) {
                response = nestedResponse;
            }
        }
        if (response == null) {
            return;
        }
        nestedResponses.remove(response);
        response.setParentResponse(null);
    }
    
    public ImporterResponse[] getNestedResponses() {
        return nestedResponses.toArray(EMPTY_RESPONSES);
    }
    
    private void setParentResponse(ImporterResponse parentResponse) {
        this.parentResponse = parentResponse;
    }
}
