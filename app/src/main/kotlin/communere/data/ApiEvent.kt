/*
 * Copyright (C) 2020 Hamidreza Etebarian & Ali Modares.
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

package communere.data

import android.content.res.Resources
import com.etebarian.meowframework.R

/**
 * Api Event class.
 *
 * @author  Hamidreza Etebarian
 * @version 1.0.0
 * @since   2020-03-01
 */

sealed class ApiEvent<T> {

    abstract val data: T?

    data class Loading(override val data: Boolean =false) : ApiEvent<Boolean>()

    data class Success<T>(override val data: T) : ApiEvent<T>()

    data class Error<T>(override val data: T) : ApiEvent<T>()

    data class Cancellation(override val data: Nothing? = null) : ApiEvent<Nothing>() {
        fun title(resources: Resources) = resources.getString(R.string.error_cancellation_title)
        fun message(resources: Resources) =
            resources.getString(R.string.error_cancellation_message)
    }

}