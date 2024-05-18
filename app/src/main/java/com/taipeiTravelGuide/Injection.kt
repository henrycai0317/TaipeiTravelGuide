/*
 * Copyright (C) 2018 The Android Open Source Project
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

package com.taipeiTravelGuide

import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.taipeiTravelGuide.connect.TravelService
import com.taipeiTravelGuide.data.TravelRepository
import com.taipeiTravelGuide.factory.SeeMoreAttractionsViewModelFactory
import com.taipeiTravelGuide.factory.SeeMoreEventsViewModelFactory



object Injection {


    private fun provideTravelRepository(): TravelRepository {
        return TravelRepository(TravelService())
    }


    fun provideSeeMoreEventsViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return SeeMoreEventsViewModelFactory(owner, provideTravelRepository())
    }

    fun provideSeeMoreAttractionsViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return SeeMoreAttractionsViewModelFactory(owner, provideTravelRepository())
    }
}
