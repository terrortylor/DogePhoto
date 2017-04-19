# DogePhoto
A Java app to Doge a photo

# Requires
* mvn

# Usage
```
import to.dogemypho.DogePhoto;

DogePhoto doge;
doge = new DogePhoto();

doge.dogeMyPhoto(String fileName, int fontPointSize, List<String> dogeText);
```
Will output a file called 'newimage.jpg'

# Example
![Alt text](src/test/resources/images/doge.jpg?raw=true "Original")

![Alt text](newimage.jpg?raw=true "Doge'd")
