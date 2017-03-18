package autokatta.com.interfaces;

import retrofit2.Response;

/**
 * Created by ak-001 on 18/3/17.
 */

public interface RequestNotifier {
    void notifySuccess(Response<?> response);
    void notifyError(Throwable error);
}
