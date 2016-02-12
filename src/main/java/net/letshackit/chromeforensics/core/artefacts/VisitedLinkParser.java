/*
 * Copyright 2015 Psycho_Coder <Animesh Shaw>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.letshackit.chromeforensics.core.artefacts;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.LinkedList;

/**
 * Class that parses the
 * <pre>Visited Links</pre> file and gathers the information contained
 *
 * @author Psycho_Coder
 */
public class VisitedLinkParser {

    private final LinkedList<String> visitedUrls;
    private File vLnkFile;

    public VisitedLinkParser() {
        visitedUrls = new LinkedList<>();
    }

    public VisitedLinkParser(File vLnkFile) {
        this.vLnkFile = vLnkFile;
        visitedUrls = new LinkedList<>();
    }

    public void parse() {

    }

    public void parse(File file) {

    }

    public void parse(Path path) {
        parse(path.toFile());
    }

    public void parse(String file) {
        parse(new File(file));
    }

    public boolean isVisited(String url) {
        return visitedUrls.contains(url);
    }

    public boolean isVisited(URL url) {
        return false;
    }

}
