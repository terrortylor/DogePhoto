package to.dogemypho;

import static org.junit.Assert.*;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.objenesis.instantiator.basic.NewInstanceInstantiator;

public class DogePhotoTest {
	private static final String SO_FUN = "so fun";

	private static final String MANY_TESTS = "many tests";

	private static final String SUCH_FUN = "such fun";

	private static final String WOW = "wow";

	private static final String VALID_FILENAME = "src/test/resources/images/doge.jpg";

	private static final String INVALID_FILENAME_JPG = "invalid_filename.jpg";

	DogePhoto testObj;

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Mock Random randomGeneratorMock;
	@Mock List<String> userDogeTextListMock;
	@Mock Iterator<String> userDogeTextIteratorMock;
	@Mock BufferedImage bufferedImageMock;
	@Mock Graphics graphicsMock;
	@Mock Font fontMock;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	    randomGeneratorMock = Mockito.mock(Random.class);
		testObj = new DogePhoto();
	
//		userDogeTextList = new ArrayList<String>();
//		userDogeTextList.add(WOW);
//		userDogeTextList.add(SUCH_FUN);
//		userDogeTextList.add(MANY_TESTS);
//		userDogeTextList.add(SO_FUN);
//		Mockito.doReturn(WOW).when(userDogeTextListMock).get(0);
//		Mockito.doReturn(SUCH_FUN).when(userDogeTextListMock).get(1);
//		Mockito.doReturn(MANY_TESTS).when(userDogeTextListMock).get(2);
//		Mockito.doReturn(SO_FUN).when(userDogeTextListMock).get(3);
//		userDogeTextIteratorMock = Mockito.mock(Iterator.class);
//		Mockito.doReturn(userDogeTextIteratorMock).when(userDogeTextList).iterator();
//		Mockito.doReturn(WOW).when(userDogeTextIteratorMock).hasNext();
//		Mockito.doReturn(SUCH_FUN).when(userDogeTextIteratorMock).hasNext();
//		Mockito.doReturn(MANY_TESTS).when(userDogeTextIteratorMock).hasNext();
//		Mockito.doReturn(SO_FUN).when(userDogeTextIteratorMock).hasNext();
		
		
		userDogeTextIteratorMock = Mockito.mock(Iterator.class);
        Mockito.when(userDogeTextIteratorMock.hasNext()).thenReturn(true, true, true, true, false);
        Mockito.when(userDogeTextIteratorMock.next()).thenReturn(WOW)
            .thenReturn(SUCH_FUN).thenReturn(MANY_TESTS).thenReturn(SO_FUN);

        userDogeTextListMock = Mockito.mock(List.class);
        Mockito.when(userDogeTextListMock.iterator()).thenReturn(userDogeTextIteratorMock);

        bufferedImageMock = Mockito.mock(BufferedImage.class);
	}

	@Test
	public void testDogeMyPhoto_Null_FileName() throws Exception {
		exception.expect(Exception.class);
		exception.expectMessage(DogePhoto.FILENAME_CANNOT_BE_EMPTY);

		testObj.dogeMyPhoto(null, 50, Collections.<String> emptyList());
	}

	@Test
	public void testDogeMyPhoto_Blank_FileName() throws Exception {
		exception.expect(Exception.class);
		exception.expectMessage(DogePhoto.FILENAME_CANNOT_BE_EMPTY);

		testObj.dogeMyPhoto("", 50, Collections.<String> emptyList());
	}

	@Test
	public void testDogeMyPhoto_Zero_orLess_FontPointSize() throws Exception {
		exception.expect(Exception.class);
		exception.expectMessage(DogePhoto.FONT_POINT_SIZE_SHOULD_BE_GREATER_THAN_0);

		testObj.dogeMyPhoto(INVALID_FILENAME_JPG, 0, Collections.<String> emptyList());
	}

	@Test
	public void testDogeMyPhoto_Null_Text_List() throws Exception {
		exception.expect(Exception.class);
		exception.expectMessage(DogePhoto.EMPTY_TEXT_LIST);

		testObj.dogeMyPhoto(INVALID_FILENAME_JPG, 10, null);
	}

	@Test
	public void testDogeMyPhoto_Empty_Text_List() throws Exception {
		exception.expect(Exception.class);
		exception.expectMessage(DogePhoto.EMPTY_TEXT_LIST);

		testObj.dogeMyPhoto(INVALID_FILENAME_JPG, 10, Collections.<String> emptyList());
	}

	@Test
	public void testDogeMyPhoto_Iterate_All_List() throws Exception{
		testObj.setRandomGenerator(new Random());

		Mockito.when(userDogeTextIteratorMock.next()).thenReturn(WOW);
		Mockito.when(userDogeTextIteratorMock.next()).thenReturn(SUCH_FUN);
		Mockito.when(userDogeTextIteratorMock.next()).thenReturn(MANY_TESTS);
		Mockito.when(userDogeTextIteratorMock.next()).thenReturn(SO_FUN);

		testObj.dogeMyPhoto(VALID_FILENAME, 10, userDogeTextListMock);

		Mockito.verify(userDogeTextIteratorMock, Mockito.times(4)).next();
		Mockito.verify(userDogeTextIteratorMock, Mockito.times(5)).hasNext();
	}
	
	@Test
	public void testCheckForCollision_B_Left_Collision() {
		DogeText a = new DogeText(WOW, 10, 10, 15, 10);
		DogeText b = new DogeText(WOW, 15, 11, 15, 10);
		
		boolean result = testObj.checkForCollision(a, b);
		
		assertTrue("Collision expected", result);
	}
	
	@Test
	public void testCheckForCollision_B_Left_No_Collision() {
		DogeText a = new DogeText(WOW, 10, 10, 15, 10);
		DogeText b = new DogeText(WOW, 15, 30, 15, 10);
		
		boolean result = testObj.checkForCollision(a, b);
		
		assertFalse("Collision not expected", result);
	}
	
	@Test
	public void testCheckForCollision_B_Right_Collision() {
		DogeText a = new DogeText(WOW, 10, 10, 15, 10);
		DogeText b = new DogeText(WOW, 5, 11, 15, 10);
		
		boolean result = testObj.checkForCollision(a, b);
		
		assertTrue("Collision expected", result);
	}
	
	@Test
	public void testCheckForCollision_B_Right_No_Collision() {
		DogeText a = new DogeText(WOW, 10, 10, 15, 10);
		DogeText b = new DogeText(WOW, 5, 30, 15, 10);
		
		boolean result = testObj.checkForCollision(a, b);
		
		assertFalse("Collision expected", result);
	}
	
	@Test
	public void testCheckForCollision_B_Top_Collision() {
		DogeText a = new DogeText(WOW, 10, 10, 15, 10);
		DogeText b = new DogeText(WOW, 15, 5, 15, 10);
		
		boolean result = testObj.checkForCollision(a, b);
		
		assertTrue("Collision expected", result);
	}
	
	@Test
	public void testCheckForCollision_B_Top_No_Collision() {
		DogeText a = new DogeText(WOW, 10, 10, 15, 10);
		DogeText b = new DogeText(WOW, 30, 5, 15, 10);
		
		boolean result = testObj.checkForCollision(a, b);
		
		assertFalse("Collision not expected", result);
	}
	
	@Test
	public void testCheckForCollision_B_Bottom_Collision() {
		DogeText a = new DogeText(WOW, 10, 10, 15, 10);
		DogeText b = new DogeText(WOW, 5, 5, 15, 10);
		
		boolean result = testObj.checkForCollision(a, b);
		
		assertTrue("Collision expected", result);
	}
	
	@Test
	public void testCheckForCollision_B_Bottom_No_Collision() {
		DogeText a = new DogeText(WOW, 10, 10, 15, 10);
		DogeText b = new DogeText(WOW, 30, 5, 15, 10);
		
		boolean result = testObj.checkForCollision(a, b);
		
		assertFalse("Collision not expected", result);
	}
	
	@Test
	public void testCheckForCollision_B_in_A() {
		DogeText a = new DogeText(WOW, 10, 10, 40, 40);
		DogeText b = new DogeText(WOW, 15, 15, 5, 5);
		
		boolean result = testObj.checkForCollision(a, b);
		
		assertTrue("Collision expected", result);
	}

	@Test
	public void testCreateNewText() {
		int x = 40;
		int y = 30;
		int textRightPoint = 50;
		int textBottomPoint = 35;
		int textWidth = 10;
		int textHeight = 5;
		int imageWidth = 100;
		int imageHeight = 200;
		int imageWidthMinusTextWidth = 90;
		int imageHeightMinusTextHeight = 195;

		testObj.setRandomGenerator(randomGeneratorMock);
		Mockito.when(randomGeneratorMock.nextInt(imageWidthMinusTextWidth)).thenReturn(x);
		Mockito.when(randomGeneratorMock.nextInt(imageHeightMinusTextHeight)).thenReturn(y);
	
		DogeText result = testObj.createNewText(WOW, imageWidth, imageHeight, textWidth, textHeight);

		assertEquals(x, result.getLeft());
		assertEquals(y, result.getTop());
		assertEquals(textRightPoint, result.getRight());
		assertEquals(textBottomPoint, result.getBottom());
	}

	@Test
	public void testLoadImage_Incorrect_FileName() throws IOException {
		exception.expect(IOException.class);
		exception.expectMessage("Can't read input file!");

		BufferedImage result = testObj.loadImage(INVALID_FILENAME_JPG);

		assertNotNull(result);
	}

	@Test
	public void testLoadImage_Correct_FileName() throws IOException {
		BufferedImage result = testObj.loadImage(VALID_FILENAME);
	
		assertNotNull(result);
	}

	@Test
	public void testGetTextMetrics_withText() throws IOException {
		BufferedImage image = testObj.loadImage(VALID_FILENAME);

		int[] result = testObj.getTextMetrics(image, "Short text");

		assertNotNull(result);
		assertEquals(-1, Integer.compare(0, result[0]));
		assertEquals(-1, Integer.compare(0, result[1]));
		assertEquals(55, result[0]);
		assertEquals(15, result[1]);
	}

	@Test
	public void testGetTextMetrics_withoutText() throws IOException {
		BufferedImage image = testObj.loadImage(VALID_FILENAME);

		int[] result = testObj.getTextMetrics(image, "");

		assertNotNull(result);
		assertEquals(0, Integer.compare(0, result[0]));
		assertEquals(-1, Integer.compare(0, result[1]));
		assertEquals(15, result[1]);
	}

	@Test
	public void testAddTextToImage() {
		DogeText dogeText = new DogeText(WOW, 10, 10, 40, 40);
		Mockito.when(bufferedImageMock.getGraphics()).thenReturn(graphicsMock);
		Mockito.when(graphicsMock.getFont()).thenReturn(fontMock);

		
		testObj.addTextToImage(bufferedImageMock, dogeText);
		
		Mockito.verify(bufferedImageMock).getGraphics();
		Mockito.verify(graphicsMock).drawString(dogeText.getText(), dogeText.getLeft(), dogeText.getTop());
		Mockito.verify(graphicsMock).dispose();
	}
}
