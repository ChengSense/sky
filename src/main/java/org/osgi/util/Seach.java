package org.osgi.util;

import java.util.List;
import java.util.Objects;

public abstract class Seach<E> {
	private E[] a;
	private List<E> b;
	protected int i = 0, size = 0;

	public int size() {
		return size;
	}

	public void size(int size) {
		this.size = size;
	}

	public E next() {
		if (i < size - 1)
			return a[i + 1];
		return null;
	}

	public E prev() {
		if (i > 0)
			return a[i - 1];
		return null;
	}

	public Seach(E[] a) {
		this.a = a;
		this.size(a.length);
		for (i = 0; i < size; i++)
			this.each(a[i]);
		end(a[size - 1]);
	}

	public Seach(List<E> a) {
		this.b = a;
		this.size(a.size());
		for (i = 0; i < size; i++)
			this.each(a.get(i));
		end(a.get(size - 1));
	}

	public Seach(E[] a, boolean b) {
		this.a = a;
		this.size(a.length);
		first(a[0]);
		for (i = 1; i < size; i++)
			this.each(a[i]);
		end(a[size - 1]);
	}

	public Seach(E[] a, boolean b, boolean c) {
		this.a = a;
		this.size(a.length);
		first(a[0]);
		for (i = 1; i < size - 1; i++)
			this.each(a[i]);
		last(a[size - 1]);
		end(a[size - 1]);
	}

	void first(E o) {

	}

	public abstract void each(E o);

	void last(E o) {

	}

	void end(E o) {

	}

}