package com.balarawool.bootloom.abank.joiners;

import com.balarawool.bootloom.abank.domain.Model.NodeProbe;
import com.balarawool.bootloom.abank.domain.Model.RegionProbeResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.atomic.AtomicInteger;

public final class MajorityJoiner implements StructuredTaskScope.Joiner<NodeProbe, RegionProbeResult> {
    private final String regionName;
    private final int total;
    private final int majority; // >50%
    private final List<NodeProbe> successes = Collections.synchronizedList(new ArrayList<>());
    private final List<NodeProbe> all = Collections.synchronizedList(new ArrayList<>());
    private final AtomicInteger okCount = new AtomicInteger(0);

    public MajorityJoiner(String regionName, int totalNodes) {
        this.regionName = regionName;
        if (totalNodes <= 0) throw new IllegalArgumentException("totalNodes must be > 0");
        this.total = totalNodes;
        this.majority = (totalNodes / 2) + 1;
    }

    @Override
    public boolean onComplete(StructuredTaskScope.Subtask<? extends NodeProbe> subtask) {
        // Collect each probe outcome
        switch (subtask.state()) {
            case SUCCESS -> {
                NodeProbe np = subtask.get();
                all.add(np);
                if (np.ok()) {
                    successes.add(np);
                    if (okCount.incrementAndGet() >= majority) {
                        // Majority reached -> cancel rest
                        return true; // request scope cancellation
                    }
                }
            }
            case FAILED -> {
                var err = subtask.exception();
                // Represent failed probe as not OK
                all.add(new NodeProbe("<unknown>", false, java.time.Duration.ZERO,
                        Optional.ofNullable(err).map(Throwable::getMessage)));
            }
        }
        return false; // continue
    }

    @Override
    public RegionProbeResult result() {
        boolean active = okCount.get() >= majority;
        return new RegionProbeResult(regionName, okCount.get(), total, active,
                Collections.unmodifiableList(all));
    }
}

