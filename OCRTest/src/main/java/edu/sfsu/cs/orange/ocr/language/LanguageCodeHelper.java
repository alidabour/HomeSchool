/*
 * Copyright 2011 Robert Theis
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
package edu.sfsu.cs.orange.ocr.language;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import edu.sfsu.cs.orange.ocr.R;

/**
 * Class for handling functions relating to converting between standard language
 * codes, and converting language codes to language names.
 */
public class LanguageCodeHelper {
    public static final String TAG = "LanguageCodeHelper";

    /**
     * Private constructor to enforce noninstantiability
     */
    private LanguageCodeHelper() {
        throw new AssertionError();
    }

    /**
     * Map an ISO 639-3 language code to a.n ISO 639-1 language code.
     *
     * There is one entry here for each language recognized by the OCR engine.
     *
     * @param languageCode
     *            ISO 639-3 language code
     * @return ISO 639-1 language code
     */

    /**
     * Map the given ISO 639-3 language code to a name of a language, for example,
     * "Spanish"
     *
     * @param context
     *            interface to calling application environment. Needed to access
     *            values from strings.xml.
     * @param languageCode
     *            ISO 639-3 language code
     * @return language name
     */
    public static String getOcrLanguageName(Context context, String languageCode) {
        Resources res = context.getResources();
        String[] language6393 = res.getStringArray(R.array.iso6393);
        String[] languageNames = res.getStringArray(R.array.languagenames);
        int len;

        // Finds the given language code in the iso6393 array, and takes the name with the same index
        // from the languagenames array.
        for (len = 0; len < language6393.length; len++) {
            if (language6393[len].equals(languageCode)) {
                Log.d(TAG, "getOcrLanguageName: " + languageCode + "->"
                        + languageNames[len]);
                return languageNames[len];
            }
        }

        Log.d(TAG, "languageCode: Could not find language name for ISO 693-3: "
                + languageCode);
        return languageCode;
    }

    /**
     * Map the given ISO 639-1 language code to a name of a language, for example,
     * "Spanish"
     *
     * @param languageCode
     *             ISO 639-1 language code
     * @return name of the language. For example, "English"
     */


}
