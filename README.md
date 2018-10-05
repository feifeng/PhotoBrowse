
<img src="./Images/sleepwind-logo.png">

# PhotoBrowse - Android
PhotoBrowse is an Android photo browser that supports dragging off

## Preview

| Photo Click | Photo Drag  | Known issue  |
| ------------- | ------------- | ------------- |
| ![Photo Click](./Images/photo-click.gif)  | ![Photo Drag](./Images/photo-drag.gif)  | ![Known issue](./Images/known-issue.gif)  |



## Features

- Basic photo browsing
- Click to close the photo
- Drag to close the photo

## Known issue
- 

## Installation 

### Requirements 

- Android SDK Version 21 
- Java Development language

## Usage

```java 

public void onItemClick(View view) {
        int position = photoRecyclerView.getChildAdapterPosition(view);
        Intent intent = new Intent(this, BrowseActivity.class);
        intent.putStringArrayListExtra("PHOTO_PATH", photoPathList);
        intent.putExtra("index", position);

        ImageView imageView = view.findViewById(R.id.photoImageView);

        if (Build.VERSION.SDK_INT >= 22) {
            ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, imageView, photoPathList.get(position));
            startActivity(intent, compat.toBundle());
        }else {
            startActivity(intent);
        }
    }

```

## Author
<a src="https://github.com/weifans">
    <img src="./Images/avatar.png" style="" width="100px">
</a>


Does your organization or project use PhotoBrowse? Please let me know by email.

- weifans, duanhaisi@gmail.com
- SLEEP WIND https://sleepwind.com

## License 

PhotoBrowse is available under the MIT license. See the LICENSE file for more info.
