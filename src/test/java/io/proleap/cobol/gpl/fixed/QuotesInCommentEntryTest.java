package io.proleap.cobol.gpl.fixed;

import java.io.File;

import io.proleap.cobol.applicationcontext.Cobol85GrammarContextFactory;
import io.proleap.cobol.preprocessor.Cobol85Preprocessor.Cobol85SourceFormatEnum;
import io.proleap.cobol.runner.Cobol85ParseTestRunner;
import io.proleap.cobol.runner.impl.Cobol85ParseTestRunnerImpl;
import org.junit.Test;

public class QuotesInCommentEntryTest {

	@Test
	public void test() throws Exception {
		Cobol85GrammarContextFactory.configureDefaultApplicationContext();

		final File inputFile = new File("src/test/resources/io/proleap/cobol/gpl/fixed/QuotesInCommentEntry.cbl");
		final Cobol85ParseTestRunner runner = new Cobol85ParseTestRunnerImpl();
		runner.parseFile(inputFile, Cobol85SourceFormatEnum.FIXED);
	}
}