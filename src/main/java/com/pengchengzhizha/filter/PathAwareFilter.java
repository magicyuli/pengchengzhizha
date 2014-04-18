package com.pengchengzhizha.filter;

import javax.servlet.Filter;

public interface PathAwareFilter extends Filter {
	String getFilterPath();
}
