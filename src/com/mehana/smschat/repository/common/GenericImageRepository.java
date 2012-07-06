package com.mehana.smschat.repository.common;

import com.mehana.smschat.exception.CommonException;
import com.mehana.smschat.exception.UploadException;
import com.mehana.smschat.model.common.AbstractImage;
import com.mehana.smschat.model.common.AbstractImageGallery;

import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;

public interface GenericImageRepository<T extends AbstractImage, I extends AbstractImageGallery> {

	void removeImage(T entity) throws CommonException;

	void updateImage(T entity);

	void uploadImage(T entity, UploadedFile uploadedFile) throws UploadException;

	void uploadGallery(T entity, I entityImage, UploadedFile uploadedFile) throws UploadException;

}
