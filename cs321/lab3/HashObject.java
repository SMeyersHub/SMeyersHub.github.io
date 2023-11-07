/**
 * Represents an object being hashed into a table. Comes with comparison for Integer, Long, and String Objects
 * @author Steven Meyers
 * @param <T>
 * @date 8/3/2020
 */
public class HashObject<T> {
	T key = null;
	int duplicateCount = 0;
	int probeCount = 0;
	public HashObject(T o) {
		key = o;
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof Integer && key instanceof Integer) {
			if(o == key) {
				return true;
			}
			else {
				return false;
			}
		} else if (o instanceof Long && key instanceof Long) {
			if(Long.compare((Long)key, (Long)o) == 0) {
				return true;
			}
			else {
				return false;
			}
		} else if(o instanceof String && key instanceof String) {
			if(((String) o).compareTo((String) key) == 0) {
				return true;
			} else {
				return false;
			}
		}
		
		@SuppressWarnings("unchecked")
		HashObject<T> object = (HashObject<T>) new HashObject<Object>(o);
		return key == object.getKey();
	}
	
	public void incrementDuplicate() {
		duplicateCount++;
	}
	
	public int getDuplicateCount() {
		return duplicateCount;
	}
	
	public void incrementProbes() {
		probeCount++;
	}
	
	public int getProbeCount() {
		return probeCount;
	}
	
	public void setKey(T key) {
		this.key = key;
	}
	public T getKey() {
		return key;
	}
	
	@Override
	public String toString() {
		String string = key + " " + duplicateCount + " " + probeCount;
		return string;
	}
	
}

