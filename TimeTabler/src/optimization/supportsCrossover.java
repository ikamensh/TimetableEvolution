package optimization;

import java.util.Set;

public interface supportsCrossover {

	public supportsCrossover schufflePhenoParents(Set<supportsCrossover> parents, boolean timeAndRoomFromSameParent);

}
