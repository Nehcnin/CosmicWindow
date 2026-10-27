# Part II - Applied Android Project Guide

Part II applies the foundation from the [Native Android Developer Training Guide](ANDROID-TRAINING-GUIDE.md) to a realistic app brief. The trainer should define the product brief, team size, review cadence, and calendar duration before this phase begins. Choose a small enough problem to complete end-to-end; favor a reliable, well-tested core flow over a broad collection of unfinished features.

## Current applied project: Cosmic Window

### Project overview

Cosmic Window is an Android app for people who enjoy space photography. It lets users:

- discover NASA's picture of the day;
- read the story behind the picture;
- watch the Earth move through a sequence of satellite images;
- search NASA's public image library;
- save favorite stories and images.

One student will build the app in three weeks, working six hours per day for a total of about 90 hours. Mentors provide the visual design; the student implements the Android app with Kotlin and Jetpack Compose.

**Visual design:** [Cosmic Window in Figma](https://www.figma.com/design/Ns4M9UMQgDVLrJ9jxSpW8d/Cosmic-Window).

### Main navigation

The bottom navigation has four main destinations:

1. **Today**
2. **Earth**
3. **Explore**
4. **Saved**

Detail screens do not show the bottom navigation; they use a back button instead.

### Screen flow

```text
Today
  -> APOD Story Detail

Earth
  -> stays on the Earth screen

Explore
  -> NASA Image Detail
  -> Full-Screen Image Viewer

Saved APOD item
  -> APOD Story Detail

Saved NASA image
  -> NASA Image Detail
  -> Full-Screen Image Viewer
```

### Screens and requirements

#### 1. Today

Today is the first screen of the app. It shows NASA's current Astronomy Picture of the Day (APOD).

It shows today's image or video thumbnail, title, date, short description, image credit or copyright, entries from the previous seven days, and the newest video entry from the loaded days when one is available. If data cannot be refreshed, show the last saved version and an **Updated** message.

The user can:

- Tap **Read Cosmic Story** to open APOD Story Detail.
- Tap an item under **Previous 7 Days** to open that story.
- Save or remove the story with the favorite icon.
- Share through the Android Sharesheet. Share the public page link, not the downloaded image file.
- Tap **Open video in browser** to open a video in an external app or browser.
- Use bottom navigation to open Earth, Explore, or Saved.

**API:** [NASA Astronomy Picture of the Day API](https://science.nasa.gov/wp-json/wp/v2/apod-basic). Use it for title, date, explanation, media type, image, video link, credit, and copyright information. NASA is changing the APOD API, so keep all APOD code inside a separate data source and mapper.
# Part II - Applied Android Project Guide

Part II applies the foundation from the [Native Android Developer Training Guide](ANDROID-TRAINING-GUIDE.md) to a realistic app brief. The trainer should define the product brief, team size, review cadence, and calendar duration before this phase begins. Choose a small enough problem to complete end-to-end; favor a reliable, well-tested core flow over a broad collection of unfinished features.

## Current applied project: Cosmic Window

### Project overview

Cosmic Window is an Android app for people who enjoy space photography. It lets users:

- discover NASA's picture of the day;
- read the story behind the picture;
- watch the Earth move through a sequence of satellite images;
- search NASA's public image library;
- save favorite stories and images.

One student will build the app in three weeks, working six hours per day for a total of about 90 hours. Mentors provide the visual design; the student implements the Android app with Kotlin and Jetpack Compose.

**Visual design:** [Cosmic Window in Figma](https://www.figma.com/design/Ns4M9UMQgDVLrJ9jxSpW8d/Cosmic-Window).

### Main navigation

The bottom navigation has four main destinations:

1. **Today**
2. **Earth**
3. **Explore**
4. **Saved**

Detail screens do not show the bottom navigation; they use a back button instead.

### Screen flow

```text
Today
  -> APOD Story Detail

Earth
  -> stays on the Earth screen

Explore
  -> NASA Image Detail
  -> Full-Screen Image Viewer

Saved APOD item
  -> APOD Story Detail

Saved NASA image
  -> NASA Image Detail
  -> Full-Screen Image Viewer
```

### Screens and requirements

#### 1. Today

Today is the first screen of the app. It shows NASA's current Astronomy Picture of the Day (APOD).

It shows today's image or video thumbnail, title, date, short description, image credit or copyright, entries from the previous seven days, and the newest video entry from the loaded days when one is available. If data cannot be refreshed, show the last saved version and an **Updated** message.

The user can:

- Tap **Read Cosmic Story** to open APOD Story Detail.
- Tap an item under **Previous 7 Days** to open that story.
- Save or remove the story with the favorite icon.
- Share through the Android Sharesheet. Share the public page link, not the downloaded image file.
- Tap **Open video in browser** to open a video in an external app or browser.
- Use bottom navigation to open Earth, Explore, or Saved.

**API:** [NASA Astronomy Picture of the Day API](https://science.nasa.gov/wp-json/wp/v2/apod-basic). Use it for title, date, explanation, media type, image, video link, credit, and copyright information. NASA is changing the APOD API, so keep all APOD code inside a separate data source and mapper.

#### 2. APOD Story Detail

This screen shows the complete story for one APOD entry: a large image or video thumbnail, title, date, full explanation, source, credit, copyright when available, last update time, and a link to the original APOD page.

For an image, show the image. For a video, show a thumbnail and an **Open video** button; the app does not include its own video player.

The user can go back to Today or Saved, save or remove the story, share it through the Android Sharesheet, open the original APOD page in a browser, and open video content in an external app or browser.

**API:** [NASA Astronomy Picture of the Day API](https://science.nasa.gov/wp-json/wp/v2/apod-basic). Reuse the same APOD data as Today. Show locally saved data first, then refresh when needed.

#### 3. Earth in Motion

Earth in Motion shows a sequence of Earth images captured by NASA's EPIC camera. Playing them in order creates a simple time-lapse.

It shows the current Earth image, capture date and UTC time, current frame and total frame count, a timeline, previous/play-or-pause/next controls, and short information about the EPIC camera.

The user can swipe between frames, tap previous or next, drag the timeline, start or pause the time-lapse, and use bottom navigation to leave the screen.

Playback stops when the app goes into the background or the user opens another screen. Returning to Earth keeps the last selected frame but does not restart playback automatically. This screen does not need a separate detail screen.

**API:** [NASA EPIC API](https://epic.gsfc.nasa.gov/api/natural) ([documentation](https://epic.gsfc.nasa.gov/about/api)). The API returns the image name, capture time, caption, and identifier. Build the image URL from the returned image name and date:

```text
https://epic.gsfc.nasa.gov/archive/natural/{year}/{month}/{day}/jpg/{image}.jpg
```

Load only images needed for the current and nearby frames; do not download every large image before showing the screen.

#### 4. Explore NASA

Explore lets users search NASA's public image library. This project includes images only; do not show NASA videos or audio.

It shows a search field, quick search buttons such as James Webb, Mars, Moon, and Nebula, a two-column image grid, image title, creation date, favorite icon, and a loading indicator while the next page loads.

The user can enter a search term, tap a quick search, scroll results, open NASA Image Detail, save or remove an image, retry after a search or next-page request fails, and use bottom navigation to open another main screen.

Wait briefly after the user stops typing before calling the API, rather than sending a request for every keystroke. When returning from detail, preserve search text, results, and scroll position.

**API:** [NASA Image Search API example](https://images-api.nasa.gov/search?q=moon&media_type=image) and [NASA Image and Video Library API documentation](https://images.nasa.gov/docs/images.nasa.gov_api_docs.pdf).

```text
GET https://images-api.nasa.gov/search
    ?q={searchText}
    &media_type=image
    &page={page}
```

Use the NASA ID, title, description, date, source center, and preview image from the search response.














#### 5. NASA Image Detail

This screen shows one image selected from Explore or Saved. It shows a large preview image, image title, creation date, NASA ID, source or NASA center, credit when available, full description, favorite and share actions, and a **View full screen** action.

Some NASA records have missing information. Hide a missing field instead of showing an empty label. Do not invent scientific information that NASA has not returned.

The user can go back to Explore or Saved, save or remove the image, share a public link through the Android Sharesheet, and tap the image or **View full screen** to open the Full-Screen Image Viewer.

#### 2. APOD Story Detail

This screen shows the complete story for one APOD entry: a large image or video thumbnail, title, date, full explanation, source, credit, copyright when available, last update time, and a link to the original APOD page.

For an image, show the image. For a video, show a thumbnail and an **Open video** button; the app does not include its own video player.

The user can go back to Today or Saved, save or remove the story, share it through the Android Sharesheet, open the original APOD page in a browser, and open video content in an external app or browser.

**API:** [NASA Astronomy Picture of the Day API](https://science.nasa.gov/wp-json/wp/v2/apod-basic). Reuse the same APOD data as Today. Show locally saved data first, then refresh when needed.

#### 3. Earth in Motion

Earth in Motion shows a sequence of Earth images captured by NASA's EPIC camera. Playing them in order creates a simple time-lapse.

It shows the current Earth image, capture date and UTC time, current frame and total frame count, a timeline, previous/play-or-pause/next controls, and short information about the EPIC camera.

The user can swipe between frames, tap previous or next, drag the timeline, start or pause the time-lapse, and use bottom navigation to leave the screen.

Playback stops when the app goes into the background or the user opens another screen. Returning to Earth keeps the last selected frame but does not restart playback automatically. This screen does not need a separate detail screen.

**API:** [NASA EPIC API](https://epic.gsfc.nasa.gov/api/natural) ([documentation](https://epic.gsfc.nasa.gov/about/api)). The API returns the image name, capture time, caption, and identifier. Build the image URL from the returned image name and date:

```text
https://epic.gsfc.nasa.gov/archive/natural/{year}/{month}/{day}/jpg/{image}.jpg
```

Load only images needed for the current and nearby frames; do not download every large image before showing the screen.

#### 4. Explore NASA

Explore lets users search NASA's public image library. This project includes images only; do not show NASA videos or audio.

It shows a search field, quick search buttons such as James Webb, Mars, Moon, and Nebula, a two-column image grid, image title, creation date, favorite icon, and a loading indicator while the next page loads.

The user can enter a search term, tap a quick search, scroll results, open NASA Image Detail, save or remove an image, retry after a search or next-page request fails, and use bottom navigation to open another main screen.

Wait briefly after the user stops typing before calling the API, rather than sending a request for every keystroke. When returning from detail, preserve search text, results, and scroll position.

**API:** [NASA Image Search API example](https://images-api.nasa.gov/search?q=moon&media_type=image) and [NASA Image and Video Library API documentation](https://images.nasa.gov/docs/images.nasa.gov_api_docs.pdf).

```text
GET https://images-api.nasa.gov/search
    ?q={searchText}
    &media_type=image
    &page={page}
```

Use the NASA ID, title, description, date, source center, and preview image from the search response.














#### 5. NASA Image Detail

This screen shows one image selected from Explore or Saved. It shows a large preview image, image title, creation date, NASA ID, source or NASA center, credit when available, full description, favorite and share actions, and a **View full screen** action.

Some NASA records have missing information. Hide a missing field instead of showing an empty label. Do not invent scientific information that NASA has not returned.

The user can go back to Explore or Saved, save or remove the image, share a public link through the Android Sharesheet, and tap the image or **View full screen** to open the Full-Screen Image Viewer.

**API:** [NASA Image and Video Library API documentation](https://images.nasa.gov/docs/images.nasa.gov_api_docs.pdf). These endpoints are available:

```text
GET https://images-api.nasa.gov/metadata/{nasaId}
GET https://images-api.nasa.gov/asset/{nasaId}
```

The metadata endpoint provides extra information; the asset endpoint provides available image files. Reuse data already loaded by Explore and make another request only when more information or a better image is needed.

#### 6. Saved

Saved contains favorite APOD stories and NASA Library images. Saved items remain available after the app restarts.

It shows the number of saved items, filters for All, Daily Stories, and Images, saved APOD and NASA image cards, cached thumbnail, title, short description, date, and remove-from-favorites action.

Saved metadata can be shown without internet access. A previously viewed image may also be available from the image cache, but this is not guaranteed. Do not download all original images for permanent offline use.

The user can filter the list, open an APOD card in APOD Story Detail, open a NASA image card in NASA Image Detail, remove an item, undo a removal with a Snackbar, and use bottom navigation to open another main screen.

This screen does not call a NASA API directly. It uses a local database ([Android Room](https://developer.android.com/training/data-storage/room)). When a saved item is opened, its detail screen may refresh from the related [APOD API](https://science.nasa.gov/wp-json/wp/v2/apod-basic) or [NASA Image Library API](https://images.nasa.gov/docs/images.nasa.gov_api_docs.pdf).

#### 7. Full-Screen Image Viewer

The viewer shows a NASA Library image in a large, immersive layout. It shows a large image, title, date, source or credit, close button, favorite button, and share button.

Show only real metadata returned by NASA. If dimensions, wavelength, or other values are missing, do not show them.

The user can tap the image to hide or show controls; close or use Android back to return to NASA Image Detail; save or remove the image; and share the public image page. Zooming and panning are optional and should be implemented only after all required features are complete.

**API:** [NASA Image and Video Library API documentation](https://images.nasa.gov/docs/images.nasa.gov_api_docs.pdf). The viewer can use:

```text
GET https://images-api.nasa.gov/asset/{nasaId}
```

Select a good-quality JPEG for the device screen; do not automatically download the largest original file.

### Common screen states

Every screen that loads remote data needs:

- **Loading:** show a progress indicator or skeleton.
- **Content:** show the loaded data.
- **Empty:** explain that there is nothing to show.
- **Cached:** show saved data and the last update time.
- **Error:** show a simple message and a Retry button.

Missing data is not always an error; optional fields can simply be hidden.

### Local storage

Use Room to store favorite APOD stories, favorite NASA Library images, recent APOD data, and the latest EPIC metadata. Use Coil for image loading and image caching.

### Suggested Android tools

- Kotlin
- Jetpack Compose
- Navigation Compose
- ViewModel and `StateFlow`
- Kotlin Coroutines
- Retrofit or Ktor Client
- Kotlin Serialization or Moshi
- Room
- Coil

### Three-week plan

#### Week 1


**API:** [NASA Image and Video Library API documentation](https://images.nasa.gov/docs/images.nasa.gov_api_docs.pdf). These endpoints are available:

```text
GET https://images-api.nasa.gov/metadata/{nasaId}
GET https://images-api.nasa.gov/asset/{nasaId}
```

The metadata endpoint provides extra information; the asset endpoint provides available image files. Reuse data already loaded by Explore and make another request only when more information or a better image is needed.

#### 6. Saved

Saved contains favorite APOD stories and NASA Library images. Saved items remain available after the app restarts.

It shows the number of saved items, filters for All, Daily Stories, and Images, saved APOD and NASA image cards, cached thumbnail, title, short description, date, and remove-from-favorites action.

Saved metadata can be shown without internet access. A previously viewed image may also be available from the image cache, but this is not guaranteed. Do not download all original images for permanent offline use.

The user can filter the list, open an APOD card in APOD Story Detail, open a NASA image card in NASA Image Detail, remove an item, undo a removal with a Snackbar, and use bottom navigation to open another main screen.

This screen does not call a NASA API directly. It uses a local database ([Android Room](https://developer.android.com/training/data-storage/room)). When a saved item is opened, its detail screen may refresh from the related [APOD API](https://science.nasa.gov/wp-json/wp/v2/apod-basic) or [NASA Image Library API](https://images.nasa.gov/docs/images.nasa.gov_api_docs.pdf).

#### 7. Full-Screen Image Viewer

The viewer shows a NASA Library image in a large, immersive layout. It shows a large image, title, date, source or credit, close button, favorite button, and share button.

Show only real metadata returned by NASA. If dimensions, wavelength, or other values are missing, do not show them.

The user can tap the image to hide or show controls; close or use Android back to return to NASA Image Detail; save or remove the image; and share the public image page. Zooming and panning are optional and should be implemented only after all required features are complete.

**API:** [NASA Image and Video Library API documentation](https://images.nasa.gov/docs/images.nasa.gov_api_docs.pdf). The viewer can use:

```text
GET https://images-api.nasa.gov/asset/{nasaId}
```

Select a good-quality JPEG for the device screen; do not automatically download the largest original file.

### Common screen states

Every screen that loads remote data needs:

- **Loading:** show a progress indicator or skeleton.
- **Content:** show the loaded data.
- **Empty:** explain that there is nothing to show.
- **Cached:** show saved data and the last update time.
- **Error:** show a simple message and a Retry button.

Missing data is not always an error; optional fields can simply be hidden.

### Local storage

Use Room to store favorite APOD stories, favorite NASA Library images, recent APOD data, and the latest EPIC metadata. Use Coil for image loading and image caching.

### Suggested Android tools

- Kotlin
- Jetpack Compose
- Navigation Compose
- ViewModel and `StateFlow`
- Kotlin Coroutines
- Retrofit or Ktor Client
- Kotlin Serialization or Moshi
- Room
- Coil

### Three-week plan

#### Week 1

- Create the project structure and navigation.
- Connect the APOD API.
- Build Today and APOD Story Detail.
- Add simple APOD caching.

#### Week 2

- Connect the EPIC API.
- Build Earth in Motion.
- Connect NASA Image Search.
- Build Explore and NASA Image Detail.

#### Week 3

- Add Room favorites.
- Build Saved and Full-Screen Image Viewer.
- Add loading, empty, cached, and error states.
- Fix bugs and polish the app.
- Prepare the final demo.

### Out of scope

Do not add user accounts, cloud synchronization, a backend server, maps, asteroid tracking, space-weather alerts, push notifications, widgets, an embedded video player, permanent download of full-resolution media, or tablet-specific layouts.

### Definition of done

The project is complete when:

- all seven screens work;
- all navigation paths work;
- all three NASA APIs return and display real data;
- favorites stay saved after an app restart;
- APOD cached data works without internet;
- the Earth time-lapse can be played and paused;
- NASA image search supports more than one page;
- Saved opens the correct detail screen;
- the image viewer loads a suitable image;
- loading, empty, cached, and error states are handled;
- media credits are shown when available;
- the app can be installed and demonstrated on an Android device or emulator.

## Suggested project milestones

1. **Discover and design:** Read the Cosmic Window project summary, review the Figma design, and agree on user stories, acceptance criteria, data models, and navigation before implementation.
2. **Build the vertical slice:** Implement one complete APOD journey through Compose UI, ViewModel, repository, and a local or fake data source. Review code and decisions before expanding to other screens.
3. **Add NASA data and resilience:** Integrate the APOD, EPIC, and NASA Image Library APIs, then add Room persistence where required. Handle loading, empty, error, cached, and retry behavior; keep secrets out of source control.
4. **Harden and refine:** Add unit and UI tests for critical flows, improve accessibility, inspect lifecycle and state handling, and address defects found in review. Defer optional features until all required project criteria are met.
5. **Deliver and present:** Produce an installable build, document setup and known limitations, demonstrate the app, and explain architecture, trade-offs, testing, and one improvement that would come next.

## Learning references

- [App architecture guide](https://developer.android.com/topic/architecture)
- [Architecture recommendations](https://developer.android.com/topic/architecture/recommendations)
- [Data layer guidance](https://developer.android.com/topic/architecture/data-layer)
- [Navigation for Compose](https://developer.android.com/develop/ui/compose/navigation)
- [Room persistence library](https://developer.android.com/training/data-storage/room)
- [Background work with WorkManager](https://developer.android.com/develop/background-work/background-tasks/persistent)
- [Adaptive apps](https://developer.android.com/develop/adaptive-apps)
- [Android testing fundamentals](https://developer.android.com/training/testing/fundamentals)
- [App quality guidance](https://developer.android.com/quality)

Select only the references needed by the project. For example, WorkManager is appropriate for deferrable, persistent background work, not a default solution for every coroutine or network request.

## Project completion criteria

The project is ready to present when a reviewer can install and run it from the documented setup; the core user journey meets its acceptance criteria; errors are handled clearly; architecture and state ownership are understandable; critical behavior has automated coverage; and known limitations are documented.

## Trainer and learner working agreements

- Use short demonstrations followed by hands-on practice and code review. Spend most of the time building, debugging, explaining, and improving code.
- Create the project structure and navigation.
- Connect the APOD API.
- Build Today and APOD Story Detail.
- Add simple APOD caching.

#### Week 2

- Connect the EPIC API.
- Build Earth in Motion.
- Connect NASA Image Search.
- Build Explore and NASA Image Detail.

#### Week 3

- Add Room favorites.
- Build Saved and Full-Screen Image Viewer.
- Add loading, empty, cached, and error states.
- Fix bugs and polish the app.
- Prepare the final demo.

### Out of scope

Do not add user accounts, cloud synchronization, a backend server, maps, asteroid tracking, space-weather alerts, push notifications, widgets, an embedded video player, permanent download of full-resolution media, or tablet-specific layouts.

### Definition of done

The project is complete when:

- all seven screens work;
- all navigation paths work;
- all three NASA APIs return and display real data;
- favorites stay saved after an app restart;
- APOD cached data works without internet;
- the Earth time-lapse can be played and paused;
- NASA image search supports more than one page;
- Saved opens the correct detail screen;
- the image viewer loads a suitable image;
- loading, empty, cached, and error states are handled;
- media credits are shown when available;
- the app can be installed and demonstrated on an Android device or emulator.

## Suggested project milestones

1. **Discover and design:** Read the Cosmic Window project summary, review the Figma design, and agree on user stories, acceptance criteria, data models, and navigation before implementation.
2. **Build the vertical slice:** Implement one complete APOD journey through Compose UI, ViewModel, repository, and a local or fake data source. Review code and decisions before expanding to other screens.
3. **Add NASA data and resilience:** Integrate the APOD, EPIC, and NASA Image Library APIs, then add Room persistence where required. Handle loading, empty, error, cached, and retry behavior; keep secrets out of source control.
4. **Harden and refine:** Add unit and UI tests for critical flows, improve accessibility, inspect lifecycle and state handling, and address defects found in review. Defer optional features until all required project criteria are met.
5. **Deliver and present:** Produce an installable build, document setup and known limitations, demonstrate the app, and explain architecture, trade-offs, testing, and one improvement that would come next.

## Learning references

- [App architecture guide](https://developer.android.com/topic/architecture)
- [Architecture recommendations](https://developer.android.com/topic/architecture/recommendations)
- [Data layer guidance](https://developer.android.com/topic/architecture/data-layer)
- [Navigation for Compose](https://developer.android.com/develop/ui/compose/navigation)
- [Room persistence library](https://developer.android.com/training/data-storage/room)
- [Background work with WorkManager](https://developer.android.com/develop/background-work/background-tasks/persistent)
- [Adaptive apps](https://developer.android.com/develop/adaptive-apps)
- [Android testing fundamentals](https://developer.android.com/training/testing/fundamentals)
- [App quality guidance](https://developer.android.com/quality)

Select only the references needed by the project. For example, WorkManager is appropriate for deferrable, persistent background work, not a default solution for every coroutine or network request.

## Project completion criteria

The project is ready to present when a reviewer can install and run it from the documented setup; the core user journey meets its acceptance criteria; errors are handled clearly; architecture and state ownership are understandable; critical behavior has automated coverage; and known limitations are documented.

## Trainer and learner working agreements

- Use short demonstrations followed by hands-on practice and code review. Spend most of the time building, debugging, explaining, and improving code.
- Ask participants to explain a decision or trace data flow, not only show that a screen works.
- Prefer official Android documentation linked here when advice conflicts with older tutorials. Android APIs and recommended practices change; check the current documentation when starting a new topic.
- Keep feedback specific: identify the observed behavior, why it matters, and one actionable improvement.
- Treat the project criteria as outcomes, not a mandate to use every library listed. Introduce tools when they solve a real problem.

## Beyond this phase

Continue with the Android documentation on [performance](https://developer.android.com/topic/performance), [security best practices](https://developer.android.com/privacy-and-security/security-best-practices), [publishing](https://developer.android.com/studio/publish), and [large-screen and adaptive design](https://developer.android.com/develop/adaptive-apps). These areas can be expanded into follow-on modules based on the apps and roles learners will work on.
