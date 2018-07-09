
package io.fab.account.demo.canonicals;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class Response<T> {

	private boolean success;

	private T content;

	private List<Error> errors;

	public Response(final T content) {
		success = true;
		this.content = content;
	}

	public Response(final Error error) {
		success = false;
		errors = new ArrayList<>(1);
		errors.add(error);
	}

	public Response(final List<Error> errors) {
		this.errors = errors;
	}

	public boolean isSuccess() {
		return success;
	}

	public T getContent() {
		return content;
	}

	public List<Error> getErrors() {
		return errors;
	}

}
