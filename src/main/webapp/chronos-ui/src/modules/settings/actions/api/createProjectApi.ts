import apiCall from 'shared/utils/apiCall';
import errorAction from 'shared/utils/errorAction';
import {CREATE_PROJECT_URL} from '../../services';
import {CREATE_CURRENT_PROJECT} from '../constants';

export const createProjectApi = (params, token) => (dispatch) => {
  const apiCallParams = {
    method: 'POST',
    body: JSON.stringify({
      ...params
    }),
    headers: {
      Authorization: `Bearer ${token}`,
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*'
    }
  };
  dispatch({type: CREATE_CURRENT_PROJECT.pending});

  return apiCall(CREATE_PROJECT_URL, apiCallParams)
    .then(({data}) =>
      dispatch({
        type: CREATE_CURRENT_PROJECT.success,
        payload: {data}
      })
    )
    .catch((err) => {
      dispatch(errorAction(CREATE_CURRENT_PROJECT.failure, err));
      throw err;
    });
};
