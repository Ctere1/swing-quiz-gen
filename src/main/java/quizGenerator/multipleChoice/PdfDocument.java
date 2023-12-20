package quizGenerator.multipleChoice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.text.WordUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import quizGenerator.multipleChoice.Helpers.ResourceHelper;

public class PdfDocument {

	private MainWindow mainWindow;
	private Questions questionsClass;

	private String fontFilePathArimo = "Arimo-Regular.ttf";
	private String fontFilePathArimoBold = "Arimo-Bold.ttf";
	private String topic;

	public PdfDocument(MainWindow mainWindow, Questions questionsClass) {
		this.mainWindow = mainWindow;
		this.questionsClass = questionsClass;
	}

	public PdfDocument() {

	}

	public void generate() {
		try {
			topic = mainWindow.getTopicField().getText();
			PDDocument document = new PDDocument();

			int questionCount = questionsClass.getQuestions().size();
			int questionColumn = 2;
			int questionRow = 2;
			int pageCount = (int) Math.ceil((double) questionCount / (questionRow * questionColumn));

			for (int pageIdx = 0; pageIdx < pageCount; pageIdx++) {
				PDPage page = new PDPage(PDRectangle.A4);
				document.addPage(page);
				PDPageContentStream contentStream = new PDPageContentStream(document, page);

				float margin = 25;
				float yStart = page.getMediaBox().getHeight();
				float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
				float bottomMargin = 70;
				float tableHeight = yStart - bottomMargin;

				// Add topic
				contentStream.beginText();
				contentStream.setFont(
						PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArimoBold)), 11);
				contentStream.newLineAtOffset(margin, yStart - margin);
				contentStream.setLeading(14.5f);
				contentStream.showText(topic);
				contentStream.endText();

				// Draw the table headers
				float headerYPosition = yStart - margin;
				float headerYStart = headerYPosition;
				float tableYPosition = yStart - 20;

				// Draw the middle line
				for (int i = 0; i < 2; i++) {
					float nextXStart = margin + (tableWidth / 2) * i;
					contentStream.moveTo(nextXStart, headerYPosition);
					if (i == 1) {
						contentStream.lineTo(nextXStart, headerYStart - tableHeight);
					}
					contentStream.setLineWidth(0.5f);
					contentStream.stroke();
				}

				headerYPosition -= 15;

				// Draw the content
				for (int i = 0; i < questionColumn; i++) {
					float nextXStart = margin + (tableWidth / 2) * i;

					// Questions
					for (int j = 0; j < questionRow; j++) {
						int questionIndex = (pageIdx * questionColumn * questionRow) + (i * questionRow) + j;
						if (questionIndex < questionCount) {
							String question = questionsClass.getQuestions().get(questionIndex);
							List<String> choicesForQuestion = questionsClass.getChoices().get(questionIndex);

							contentStream.beginText();
							// Set the font
							contentStream.setFont(PDType0Font.load(document,
									ResourceHelper.getResourceAsStream(fontFilePathArimoBold)), 9);
							if (j == 0) {
								contentStream.newLineAtOffset(nextXStart + 10, tableYPosition - 50);
							} else {
								contentStream.newLineAtOffset(nextXStart + 10, tableYPosition - 450);
							}

							// QUESTION NUMBER TEXT
							contentStream.showText((questionIndex + 1) + ") ");
							contentStream.setFont(
									PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArimo)),
									9);
							contentStream.newLineAtOffset(0, -15);

							/**
							 * 
							 * TRYING NEW ALG
							 * 
							 * 
							 */
//							char[] charArray = question.toCharArray();
//							StringBuilder stringBuilder = new StringBuilder();
//							PDType0Font font = PDType0Font.load(document,
//									ResourceHelper.getResourceAsStream(fontFilePathArial));
//
//							for (int q = 0; q < charArray.length; q++) {
//								stringBuilder.append(charArray[q]);
//
//								// New line at 60 char
//								if ((q + 1) % 60 == 0) {
//									contentStream.setFont(font, 9);
//									System.out.println(stringBuilder.toString().replaceAll("[\\r\\n]", ""));
//									contentStream.showText(stringBuilder.toString().replaceAll("[\\r\\n]", ""));
//									contentStream.newLineAtOffset(0, -15);
//									stringBuilder.setLength(0); // StringBuilder'ı sıfırla
//								}
//							}
//
//							
//							if (stringBuilder.length() > 0) {
//								contentStream.setFont(font, 9);
//								System.out.println(stringBuilder.toString().replaceAll("[\\r\\n]", ""));
//								contentStream.showText(stringBuilder.toString().replaceAll("[\\r\\n]", ""));
//								contentStream.newLineAtOffset(0, -15);
//							}
							/**
							 * 
							 * TRYING NEW ALG
							 * 
							 * 
							 */

							// QUESTION TEXT
							String[] wrappedText = WordUtils.wrap(question, 55).split("\\r?\\n");
							for (int k = 0; k < wrappedText.length; k++) {
								contentStream.showText(wrappedText[k]);
								contentStream.newLineAtOffset(0, -15); // Add a space before moving to a new line
							}

							// QUESTION CHOICES write index ABCDE
							for (int k = 0; k < choicesForQuestion.size(); k++) {
								contentStream.setFont(PDType0Font.load(document,
										ResourceHelper.getResourceAsStream(fontFilePathArimoBold)), 9);
								contentStream.showText((char) ('A' + k) + ") ");

								// check choice's lenght, write text
								String[] wrappedChoice = WordUtils.wrap(choicesForQuestion.get(k), 55).split("\\r?\\n");
								contentStream.setFont(PDType0Font.load(document,
										ResourceHelper.getResourceAsStream(fontFilePathArimo)), 9);
								for (int o = 0; o < wrappedChoice.length; o++) {
									contentStream.showText(wrappedChoice[o]);
									contentStream.newLineAtOffset(0, -15); // Add a space before moving to a new line
								}

							}

							contentStream.endText();
						}
					}
				}

				// Add page number
				contentStream.beginText();
				contentStream.setFont(
						PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArimoBold)), 11);
				float centerX = (page.getMediaBox().getWidth() / 2) - 3;
				float centerY = headerYStart - tableHeight - 20;
				contentStream.newLineAtOffset(centerX, centerY);
				contentStream.showText("" + (pageIdx + 1));
				contentStream.endText();

				contentStream.close();
			}

			addAnswerKeyPage(document);

			// Check existing file name
			String pdfFileName = topic + ".pdf";

			File pdfFile = new File(pdfFileName);
			int fileCount = 1;
			while (pdfFile.exists()) {
				pdfFileName = topic + "_" + fileCount + ".pdf";
				pdfFile = new File(pdfFileName);
				fileCount++;
			}

			document.save(pdfFileName);
			document.close();

			JOptionPane.showMessageDialog(mainWindow.getMainFrame(), "PDF Generated: " + pdfFileName, "Info",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainWindow.getMainFrame(), "File not found: " + e.getMessage(),
					"File Not Found", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainWindow.getMainFrame(), "Error during PDF generation: \n" + e.getMessage(),
					"IO Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainWindow.getMainFrame(),
					"An unexpected error occurred: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addAnswerKeyPage(PDDocument document) throws IOException {
		PDPage answerKeyPage = new PDPage(PDRectangle.A4);
		document.addPage(answerKeyPage);

		PDPageContentStream contentStream = new PDPageContentStream(document, answerKeyPage);
		float margin = 25;
		float yStart = answerKeyPage.getMediaBox().getHeight() - margin;

		contentStream.beginText();
		contentStream.setFont(PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArimoBold)),
				11);
		contentStream.newLineAtOffset(margin, yStart);
		contentStream.showText("Answers");
		contentStream.endText();

		float tableWidth = answerKeyPage.getMediaBox().getWidth() - 2 * margin;
		float yPosition = yStart - 20;

		int numColumns = 10;
		int numRows = (int) Math.ceil((double) questionsClass.getAnswerKeys().size() / numColumns);

		float columnWidth = tableWidth / numColumns;
		contentStream.setFont(PDType0Font.load(document, ResourceHelper.getResourceAsStream(fontFilePathArimo)), 9);
		// Draw table content
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				int index = i * numColumns + j;
				if (index < questionsClass.getAnswerKeys().size()) {
					float xPosition = margin + j * columnWidth;

					// Draw cell borders
					contentStream.setLineWidth(0.5f);

					// Draw left border of the cell
					contentStream.moveTo(xPosition, yPosition);
					contentStream.lineTo(xPosition, yPosition - 20);
					contentStream.stroke();

					// Draw top border of the cell
					contentStream.moveTo(xPosition, yPosition);
					contentStream.lineTo(xPosition + columnWidth, yPosition);
					contentStream.stroke();

					// Draw right border of the cell
					contentStream.moveTo(xPosition + columnWidth, yPosition);
					contentStream.lineTo(xPosition + columnWidth, yPosition - 20);
					contentStream.stroke();

					// Draw bottom border of the cell
					contentStream.moveTo(xPosition, yPosition - 20);
					contentStream.lineTo(xPosition + columnWidth, yPosition - 20);
					contentStream.stroke();

					contentStream.beginText();
					contentStream.newLineAtOffset(xPosition + 5, yPosition - 13);
					contentStream.showText((index + 1) + ") " + questionsClass.getAnswerKeys().get(index));
					contentStream.endText();
				}
			}
			yPosition -= 20;
		}

		contentStream.close();
	}
}
