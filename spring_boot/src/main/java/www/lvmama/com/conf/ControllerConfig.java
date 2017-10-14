/**
 * 
 */
package www.lvmama.com.conf;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import www.lvmama.com.comm.ErrorMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fengyonggang
 *
 */
@ControllerAdvice
public class ControllerConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerConfig.class);

	@ExceptionHandler
	@ResponseBody
	public ResponseEntity<ErrorMessage> exceptionHandler(HttpServletRequest req, Exception e) {
		LOGGER.error("Excetion occurred", e);
		ErrorMessage error = new ErrorMessage();
		error.setException(e.getClass().getName());
		error.setMessage(e.getMessage());
		error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		error.setTimestamp(System.currentTimeMillis());
		error.setPath(req.getRequestURI());
		LOGGER.error(error.toString());
		return null;
	}
}
