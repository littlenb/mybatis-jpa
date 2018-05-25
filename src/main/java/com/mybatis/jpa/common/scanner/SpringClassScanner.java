package com.mybatis.jpa.common.scanner;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

/**
 * 
 * Class扫描器,封装了Spring资源解析器 {@ResourcePatternResolver}</br>
 * 1.该类的构造方法被私有化,请使用{@link Builder}创建(获取)Scanner对象</br>
 * 2.scan {@link #scan()}
 * 
 * @author svili
 *
 */
public class SpringClassScanner {

	/** the file type to scan : .class */
	public static final String RESOURCE_PATTERN = "**/*.class";

	/** packages to scan */
	private Set<String> scanPackages;

	/** more filters with relation default and */
	private Set<TypeFilter> typeFilters;

	/** filterAll or filterWhether */
	private boolean filterFlag = true;

	private SpringClassScanner() {
		scanPackages = new HashSet<String>();
		typeFilters = new HashSet<TypeFilter>();
	}

	public Set<Class<?>> scan() throws ClassNotFoundException, IOException {
		Set<Class<?>> classSet = new HashSet<>();
		if (!this.scanPackages.isEmpty()) {
			ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
			MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
			for (String pkg : this.scanPackages) {
				String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
						+ ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(pkg)) + "/"
						+ RESOURCE_PATTERN;
				Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);

				for (Resource resource : resources) {
					if (resource.isReadable()) {
						MetadataReader reader = readerFactory.getMetadataReader(resource);
						String className = reader.getClassMetadata().getClassName();

						if (matched(reader, readerFactory)) {
							classSet.add(Class.forName(className));
						}
					}
				}
			}
		}
		return classSet;
	}

	private boolean matched(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException {

		if (filterFlag) {
			return filterAll(reader, readerFactory);
		} else {
			return filterWhether(reader, readerFactory);
		}
	}

	/** must to be every one of filters is matched,return true */
	private boolean filterAll(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException {
		if (!this.typeFilters.isEmpty()) {
			for (TypeFilter filter : this.typeFilters) {
				if (!filter.match(reader, readerFactory)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/** if any one of filters is matched,return true */
	private boolean filterWhether(MetadataReader reader, MetadataReaderFactory readerFactory) throws IOException {
		if (!this.typeFilters.isEmpty()) {
			for (TypeFilter filter : this.typeFilters) {
				if (filter.match(reader, readerFactory)) {
					return true;
				}
			}
		}
		return false;
	}

	public static class Builder {

		private SpringClassScanner scanner = new SpringClassScanner();

		public Builder scanPackage(String scanPackage) {
			this.scanner.getScanPackages().add(scanPackage);
			return this;
		}

		public Builder typeFilter(TypeFilter typeFilter) {
			this.scanner.getTypeFilters().add(typeFilter);
			return this;
		}

		public Builder filterFlag(boolean filterFlag) {
			this.scanner.setFilterFlag(filterFlag);
			;
			return this;
		}

		public SpringClassScanner build() {
			return this.scanner;
		}
	}

	/** getter and setter */
	public Set<String> getScanPackages() {
		return scanPackages;
	}

	public void setScanPackages(Set<String> scanPackages) {
		this.scanPackages = scanPackages;
	}

	public Set<TypeFilter> getTypeFilters() {
		return typeFilters;
	}

	public void setTypeFilters(Set<TypeFilter> typeFilters) {
		this.typeFilters = typeFilters;
	}

	public boolean isFilterFlag() {
		return filterFlag;
	}

	public void setFilterFlag(boolean filterFlag) {
		this.filterFlag = filterFlag;
	}

}
