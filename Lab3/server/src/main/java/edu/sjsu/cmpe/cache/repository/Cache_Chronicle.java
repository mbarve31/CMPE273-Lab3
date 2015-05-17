package edu.sjsu.cmpe.cache.repository;

import edu.sjsu.cmpe.cache.domain.Entry;
import net.openhft.chronicle.map.ChronicleMapBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class Cache_Chronicle implements CacheInterface {

    private final Map<Long, Entry> chronicle_cache;

    public Cache_Chronicle() throws IOException {
        String file_name ="mydata.txt";
        File file=new File(file_name);
        chronicle_cache = ChronicleMapBuilder.of(Long.class, Entry.class).createPersistedTo(file);
    }
    @Override
    public Entry save(Entry newEntry) {
        checkNotNull(newEntry, "newEntry instance must not be null");
        chronicle_cache.put(newEntry.getKey(), newEntry);
        return newEntry;
    }

    @Override
    public Entry get(Long key) {
        checkArgument(key > 0,
                "Key was %s but expected greater than zero value", key);
        return chronicle_cache.get(key);
    }

    @Override
    public List<Entry> getAll() {
        return new ArrayList<Entry>(chronicle_cache.values());
    }


}
