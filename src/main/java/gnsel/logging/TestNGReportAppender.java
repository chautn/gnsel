package gnsel.logging;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;
import org.testng.Reporter;

public class TestNGReportAppender extends AppenderSkeleton {

	@Override
	public void close() {
		//
	}

	@Override
	public boolean requiresLayout() {
		return true;
	}

	@Override
	protected void append(final LoggingEvent arg0) {
		Reporter.log(eventToString(arg0) + "<br/>");
	}
	
	private String eventToString(final LoggingEvent arg0) {
		StringBuilder result = new StringBuilder(layout.format(arg0));
		if (layout.ignoresThrowable()) {
			String[] s = arg0.getThrowableStrRep();
			if (s != null) {
				for (String value : s) {
					result.append(value).append(Layout.LINE_SEP);
				}
			}
		}
		return result.toString();
	}

}
