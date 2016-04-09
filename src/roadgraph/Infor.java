package roadgraph;

import geography.GeographicPoint;

public class Infor {
	Double Length ;
	GeographicPoint position;
	
	public Infor(Double length, GeographicPoint pos)
	{
		this.Length = length;
		this.position = pos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Length == null) ? 0 : Length.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Infor other = (Infor) obj;
		if (Length == null) {
			if (other.Length != null)
				return false;
		} else if (!Length.equals(other.Length))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}

	

}
