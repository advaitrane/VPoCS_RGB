# VPoCS_RGB
Visual Perception of Colour Spectrum using Virtual Reality
Study colour perception by showing subjects three different colours(red, blue, green), using a virtual reality headset.
The app will splash a colour on the screen placed inside a VR headset. Colours are shown using VR headset to fill the field of vision.

Find code in app->src->main->vpocs_rgb
- IPActivity: Takes the IP address and port to connect to, to send the training data labels(RED/BLUE/GREEN).
- MainActivity: Splash the colour on the phone screen running the app.

Colours are loaded as .png images. The images can be found in app->src->main->assets.
To load different colours or images, store the images here and pass the appropriate filename to LoadPhotoSphere in MainActivity.
