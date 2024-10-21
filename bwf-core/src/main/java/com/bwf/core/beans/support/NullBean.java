package com.bwf.core.beans.support;

import com.bwf.common.annotation.bootstrap.annotation.Nullable;

public final class NullBean {

	NullBean() {
	}


	@Override
	public boolean equals(@Nullable Object obj) {
		return (this == obj || obj == null);
	}

	@Override
	public int hashCode() {
		return NullBean.class.hashCode();
	}

	@Override
	public String toString() {
		return "null";
	}

}
