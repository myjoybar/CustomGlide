package com.joy.glide.library.load;

import com.joy.glide.library.load.engine.Resource;

import java.util.Arrays;
import java.util.Collection;


public class MultiTransformation<T> implements Transformation<T> {
	private final Collection<? extends Transformation<T>> transformations;
	private String id;

	@SafeVarargs
	public MultiTransformation(Transformation<T>... transformations) {
		if (transformations.length < 1) {
			throw new IllegalArgumentException("MultiTransformation must contain at least one Transformation");
		}
		this.transformations = Arrays.asList(transformations);
	}

	public MultiTransformation(Collection<? extends Transformation<T>> transformationList) {
		if (transformationList.size() < 1) {
			throw new IllegalArgumentException("MultiTransformation must contain at least one Transformation");
		}
		this.transformations = transformationList;
	}

	@Override
	public Resource<T> transform(Resource<T> resource, int outWidth, int outHeight) {
		Resource<T> previous = resource;

		for (Transformation<T> transformation : transformations) {
			Resource<T> transformed = transformation.transform(previous, outWidth, outHeight);
			if (previous != null && !previous.equals(resource) && !previous.equals(transformed)) {
				previous.recycle();
			}
			previous = transformed;
		}
		return previous;
	}

	@Override
	public String getId() {
		if (id == null) {
			StringBuilder sb = new StringBuilder();
			for (Transformation<T> transformation : transformations) {
				sb.append(transformation.getId());
			}
			id = sb.toString();
		}
		return id;
	}
}
