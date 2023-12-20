package quizGenerator.multipleChoice;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import com.spire.doc.AutoFitBehaviorType;
import com.spire.doc.Document;
import com.spire.doc.FieldType;
import com.spire.doc.FileFormat;
import com.spire.doc.HeaderFooter;
import com.spire.doc.Section;
import com.spire.doc.Table;
import com.spire.doc.TableCell;
import com.spire.doc.TableRow;
import com.spire.doc.documents.BorderStyle;
import com.spire.doc.documents.BreakType;
import com.spire.doc.documents.HorizontalAlignment;
import com.spire.doc.documents.PageSize;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.ParagraphStyle;
import com.spire.doc.documents.RowAlignment;
import com.spire.doc.documents.Style;
import com.spire.doc.documents.TableRowHeightType;
import com.spire.doc.documents.TextAlignment;
import com.spire.doc.fields.Field;
import com.spire.doc.fields.TextRange;

public class WordDocument {

	private MainWindow mainWindow;
	private Questions questionsClass;

	private String topic;
	private String author;
	private static String answersString = "Answers";

	public WordDocument(MainWindow mainWindow, Questions questionsClass) {
		this.mainWindow = mainWindow;
		this.questionsClass = questionsClass;
	}

	public WordDocument() {

	}

	public String getAnswersString() {
		return answersString;
	}

	public void setAnswersString(String answersString) {
		WordDocument.answersString = answersString;
	}

	public void generate() {
		try {
			topic = mainWindow.getTopicField().getText();
			author = mainWindow.getAuthorField().getText();

			Document document = new Document();

			Section sec = document.addSection();
			sec.getPageSetup().setPageSize(PageSize.A4);
			sec.getPageSetup().getMargins().setTop(56.75f); // 2cm
			sec.getPageSetup().getMargins().setBottom(56.75f);// 2cm
			sec.getPageSetup().getMargins().setLeft(71f); // 2.5cm
			sec.getPageSetup().getMargins().setRight(56.75f);// 2cm
			// Get header and footer from a section
			HeaderFooter header = sec.getHeadersFooters().getHeader();
			HeaderFooter footer = sec.getHeadersFooters().getFooter();

			// Add a Header
			Table headerTable1 = header.addTable(false);
			headerTable1.resetCells(1, 2);
			headerTable1.getTableFormat().getBorders().getRight().setBorderType(BorderStyle.Outset);
			headerTable1.getTableFormat().getBorders().getLeft().setBorderType(BorderStyle.Outset);
			headerTable1.getTableFormat().getBorders().getTop().setBorderType(BorderStyle.Outset);
			headerTable1.getTableFormat().getBorders().getHorizontal().setBorderType(BorderStyle.Outset);

			headerTable1.getTableFormat().getBorders().getRight().setLineWidth(0.2f); // 1/4 pt
			headerTable1.getTableFormat().getBorders().getLeft().setLineWidth(0.2f); // 1/4 pt
			headerTable1.getTableFormat().getBorders().getTop().setLineWidth(0.2f); // 1/4 pt
			headerTable1.getTableFormat().getBorders().getHorizontal().setLineWidth(0.2f); // 1/4 pt

			headerTable1.getTableFormat().getPaddings().setAll(6f);

			Table headerTable2 = header.addTable(false);
			headerTable2.resetCells(1, 2);

			TableRow headerRow1 = headerTable1.getRows().get(0);
			TableRow headerRow2 = headerTable2.getRows().get(0);

			Paragraph headerParagraph = headerRow1.getCells().get(0).addParagraph();
			// Add text to the header paragraph
			TextRange text = headerParagraph.appendText(topic);
			text.getCharacterFormat().setFontName("TimesNewRoman");
			text.getCharacterFormat().setFontSize(11);

			headerRow2.setHeight(5.75f); // 0,2 cm -->0.75f == 0,02 cm
			headerRow2.setHeightType(TableRowHeightType.Exactly);
			headerRow2.getCells().get(0).getCellFormat().setBackColor(new Color(220, 220, 220));
			headerRow2.getCells().get(1).getCellFormat().setBackColor(new Color(220, 220, 220));
			// Add a break line after the table (addParagraph() will add break)
			@SuppressWarnings("unused")
			Paragraph breakLineParagraph = header.addParagraph();

			// Add a Footer
			Table footerTable1 = footer.addTable(false);
			footerTable1.resetCells(1, 2);

			Table footerTable2 = footer.addTable(false);
			footerTable2.resetCells(1, 2);
			footerTable2.getTableFormat().getPaddings().setAll(6f);

			TableRow footerRow1 = footerTable1.getRows().get(0);
			TableRow footerRow2 = footerTable2.getRows().get(0);

			Paragraph footerParagraph1 = footerRow2.getCells().get(1).addParagraph();
			Paragraph footerParagraph2 = footerRow2.getCells().get(0).addParagraph();
			// Add Field_Page and Field_Num_Pages fields to the footer paragraph
			Field pageField = footerParagraph1.appendField("Page Number", FieldType.Field_Page);
//			footerParagraph.appendText(" of ");
//			footerParagraph.appendField("Number of Pages", FieldType.Field_Num_Pages);
			pageField.getCharacterFormat().setFontName("TimesNewRoman");
			pageField.getCharacterFormat().setFontSize(9);
			footerParagraph1.getFormat().setHorizontalAlignment(HorizontalAlignment.Right);

			footerRow1.setHeight(5.75f); // 0,2 cm -->0.75f == 0,02 cm
			footerRow1.setHeightType(TableRowHeightType.Exactly);
			footerRow1.getCells().get(0).getCellFormat().setBackColor(new Color(220, 220, 220));
			footerRow1.getCells().get(1).getCellFormat().setBackColor(new Color(220, 220, 220));

			// Add text to the footer paragraph
			footerParagraph2.getFormat().setHorizontalAlignment(HorizontalAlignment.Left);
			TextRange footerText = footerParagraph2.appendText(author);
			footerText.getCharacterFormat().setFontName("TimesNewRoman");
			footerText.getCharacterFormat().setAllCaps(true);
			footerText.getCharacterFormat().setTextColor(new Color(130, 130, 130));
			footerText.getCharacterFormat().setFontSize(9);

			// Adjust the header distance
			sec.getPageSetup().setHeaderDistance(35.5f); // 1,25 cm
			// Adjust the footer distance
			sec.getPageSetup().setFooterDistance(35.5f); // 1,25 cm

			int questionCount = questionsClass.getQuestions().size();
			int questionIndex = 0;
			int questionColumn = 2;
			int questionRow = 3;
			int pageCount = (int) Math.ceil((double) questionCount / (questionRow * questionColumn));

			// Questions PAGE
			for (int pageIdx = 0; pageIdx < pageCount; pageIdx++) {
				// Add a section
				if (pageIdx != 0) {
					sec = document.addSection();
					// Adjust the header distance
					sec.getPageSetup().setHeaderDistance(35.5f); // 1,25 cm
					// Adjust the footer distance
					sec.getPageSetup().setFooterDistance(35.5f); // 1,25 cm
				}

				// Create a table
				Table table = sec.addTable(false);
				table.getTableFormat().getBorders().getVertical().setBorderType(BorderStyle.Outset);
				table.getTableFormat().getBorders().getVertical().setLineWidth(0.2f); // It doesn't affect !!
				table.resetCells(questionRow, questionColumn);

				// Questions COLUMN
				for (int i = 0; i < questionColumn; i++) {
					// Questions ROW
					for (int j = 0; j < questionRow; j++) {
						if (questionIndex < questionCount) {

							Table nestedTable = table.get(j, i).addTable();
							nestedTable.resetCells(1, 2);
							nestedTable.autoFit(AutoFitBehaviorType.Auto_Fit_To_Contents);

							String question = questionsClass.getQuestions().get(questionIndex);
							List<String> choicesForQuestion = questionsClass.getChoices().get(questionIndex);

							TableRow row = nestedTable.getRows().get(0);
							Paragraph questionNumberParagraph = row.getCells().get(0).addParagraph();
							Paragraph questionTextParagraph = row.getCells().get(1).addParagraph();

							// Check if the style already exists
							Style style = nestedTable.getDocument().getStyles().findByName("questionsStyle");
							if (style == null) {
								// Create the style if it doesn't exist
								style = new ParagraphStyle(nestedTable.getDocument());
								style.setName("questionsStyle");
								style.getCharacterFormat().setFontName("TimesNewRoman");
								style.getCharacterFormat().setFontSize(9f);
								nestedTable.getDocument().getStyles().add(style);
							}

							questionTextParagraph.applyStyle("questionsStyle");
							questionNumberParagraph.applyStyle("questionsStyle");

							questionNumberParagraph.getFormat().setFirstLineIndent(0);
							questionNumberParagraph.getFormat().setLeftIndent(-5.7f); // 0,2cm
							questionNumberParagraph.appendText((questionIndex + 1) + ".").getCharacterFormat()
									.setBold(true);

							questionTextParagraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Justify);
							questionTextParagraph.getFormat().setLeftIndent(-5.7f); // 0,2cm
							questionTextParagraph.getFormat().setRightIndent(-5.7f); // - 0,2cm
							questionTextParagraph.getFormat().setFirstLineIndent(0);
							// Set paragraph after spacing
							questionTextParagraph.getFormat().setAfterSpacing(6f); // 6pt
							// Set line spacing
							questionTextParagraph.getFormat().setLineSpacing(13.8f); // 1,15
							questionTextParagraph.appendText(question);

							Paragraph questionChoiceParagraph = row.getCells().get(1).addParagraph();
							// Questions CHOICES
							for (int k = 0; k < choicesForQuestion.size(); k++) {

								questionChoiceParagraph.applyStyle("questionsStyle");
								questionChoiceParagraph.getFormat().setLineSpacing(13.8f); // 1,15
								questionChoiceParagraph.getFormat().setAfterSpacing(6f); // 6pt
								questionChoiceParagraph.getFormat().setLeftIndent(-5.7f); // -0,2cm

								questionChoiceParagraph.appendText((char) ('A' + k) + ") ");
								questionChoiceParagraph.appendText(choicesForQuestion.get(k));
								questionChoiceParagraph = row.getCells().get(1).addParagraph();

							}
							questionIndex++;
						}
					}
				}
			}

			addAnswerKeyPage(document);
			// Save the Word document
			String docxFileName = topic + "_.docx";
			// String pdfFileName = topic + "_.pdf";

			try (FileOutputStream out = new FileOutputStream(docxFileName)) {
				// Save the result document
				document.saveToFile(docxFileName, FileFormat.Docx_2013);
				// document.saveToFile(pdfFileName);

				JOptionPane.showMessageDialog(mainWindow.getMainFrame(), "Word Document Generated: " + docxFileName,
						"Info", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(mainWindow.getMainFrame(),
					"An unexpected error occurred: \n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addAnswerKeyPage(Document document) throws IOException {
		// Add a section
		Section section = document.addSection();
		section.getPageSetup().setHeaderDistance(35.5f); // 1,25 cm
		section.getPageSetup().setFooterDistance(35.5f); // 1,25 cm
		section.getPageSetup().setPageSize(PageSize.A4);
		section.getPageSetup().getMargins().setTop(56.75f); // 2cm
		section.getPageSetup().getMargins().setBottom(56.75f);// 2cm
		section.getPageSetup().getMargins().setLeft(71f); // 2.5cm
		section.getPageSetup().getMargins().setRight(56.75f);// 2cm

		Paragraph paragraph = section.addParagraph();
		Style styleTitle = paragraph.getDocument().getStyles().findByName("answersTitleStyle");
		if (styleTitle == null) {
			styleTitle = new ParagraphStyle(paragraph.getDocument());
			styleTitle.setName("answersTitleStyle");
			styleTitle.getCharacterFormat().setFontName("TimesNewRoman");
			styleTitle.getCharacterFormat().setFontSize(9f);
			styleTitle.getCharacterFormat().setBold(true);
			styleTitle.getCharacterFormat().setAllCaps(true);
			styleTitle.getDocument().getStyles().add(styleTitle);
		}
		paragraph.appendBreak(BreakType.Line_Break);
		paragraph.appendText(answersString).getCharacterFormat().setBold(true);
		paragraph.getFormat().setLineSpacing(13.75f); // 1,15
		paragraph.getFormat().setAfterSpacing(6f); // 6pt
		paragraph.appendBreak(BreakType.Line_Break);
		paragraph.applyStyle("answersTitleStyle");

		// Create a table with one row and as many columns as there are answers
		Table table = section.addTable(true);
		int numColumns = 10;
		int numRows = (int) Math.ceil((double) questionsClass.getAnswerKeys().size() / numColumns) * 2;
		table.resetCells(numRows, numColumns);
		table.getTableFormat().setHorizontalAlignment(RowAlignment.Center);

		Style style = table.getDocument().getStyles().findByName("AnswersStyle");
		if (style == null) {
			style = new ParagraphStyle(table.getDocument());
			style.setName("AnswersStyle");
			style.getCharacterFormat().setFontName("TimesNewRoman");
			style.getCharacterFormat().setFontSize(9f);
			style.getCharacterFormat().setBold(true);
			table.getDocument().getStyles().add(style);
		}

		int j = 1;
		// Populate the table with answers
		for (int k = 0; k < numRows; k += 2) {
			TableRow indexRow = table.getRows().get(k);
			for (int i = 0; i < numColumns; i++) {
				// Set Background Color
				indexRow.getCells().get(i).getCellFormat().setBackColor(new Color(220, 220, 220));

				TableCell indexCell = indexRow.getCells().get(i);
				Paragraph indexParagraph = indexCell.addParagraph();
				indexParagraph.applyStyle("AnswersStyle");
				indexParagraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
				indexParagraph.getFormat().setTextAlignment(TextAlignment.Center);
				indexParagraph.getFormat().setBeforeSpacing(4);
				indexParagraph.getFormat().setAfterSpacing(4);
				indexParagraph.appendText((j) + "");
				;
				j++;
			}
		}

		j = 0;
		// Populate the inner table with answer keys
		for (int k = 1; k < numRows; k += 2) {
			TableRow answerRow = table.getRows().get(k);
			for (int i = 0; i < numColumns; i++) {
				if (j < questionsClass.getAnswerKeys().size()) {
					TableCell answerCell = answerRow.getCells().get(i);
					Paragraph answerParagraph = answerCell.addParagraph();
					answerParagraph.applyStyle("AnswersStyle");
					answerParagraph.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
					answerParagraph.getFormat().setTextAlignment(TextAlignment.Center);
					answerParagraph.getFormat().setBeforeSpacing(4);
					answerParagraph.getFormat().setAfterSpacing(4);
					answerParagraph.appendText(questionsClass.getAnswerKeys().get(j)).getCharacterFormat()
							.setBold(false);
					j++;
				}
			}
		}

	}

}
