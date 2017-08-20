package zkbench.cache;

public abstract class CachingBenchmarkBase
{
    protected void runStandard(CachingState state)
    {
        state.cache().wrapOperation(state::performOperation);
    }
}
