package com.talentica.blescanlib.scanning.bleConnectionModels;

/**
 * Created by NavalB on 25-05-2016.
 */
public class Characteristic {
	private final String assignedNumber;
	private final String specificationName;
	private final int formatType;
	private final DataType dataType;
	private final int offset;
	private final Object[] permissibleValue;

	Characteristic(Builder builder){
		assignedNumber = builder.assignedNumber;
		specificationName = builder.specificationName;
		formatType = builder.formatType;
		dataType = builder.dataType;
		offset = 0;
		permissibleValue = builder.permissibleValues;
	}

	public String getAssignedNumber() {
		return assignedNumber;
	}

	public String getSpecificationName() {
		return specificationName;
	}

	public int getFormatType() {
		return formatType;
	}

	public DataType getDataType() {
		return dataType;
	}

	public int getOffset() {
		return offset;
	}

	public Object[] getPermissibleValue() {
		return permissibleValue;
	}

	public static class Builder{
		private String assignedNumber;
		private String specificationName;
		private int formatType;
		private DataType dataType;
		private Object[] permissibleValues;

		Builder(){
		}

		public Builder setAssignedNumber(String assignedNumber) {
			this.assignedNumber = assignedNumber;
			return Builder.this;
		}

		public Builder setFormatType(int formatType) {
			this.formatType = formatType;
			return Builder.this;
		}

		public Builder setSpecificationName(String specificationName) {
			this.specificationName = specificationName;
			return Builder.this;
		}

		public Characteristic build(){
			return new Characteristic(this);
		}

		public Builder setDataType(DataType dataType) {
			this.dataType = dataType;
			return this;
		}

		public Builder setPermissibleValues(Object[] permissibleValues) {
			this.permissibleValues = permissibleValues;
			return this;
		}
	}


}
