import {connect} from 'react-redux';
import {compose, lifecycle, withHandlers} from 'recompose';
import sortBy from 'lodash/sortBy';

import {selectAllUserIdForReports} from 'modules/reports/actions/reports';

import {fetchUsersList} from '../../actions/api/fetchUsersList';
import {selectUserInUserlist, setDrawerStatus} from '../../actions/drawer';
import Drawer from './Drawer';

const mapStateToProps = (state) => ({
  isOpen: state.common.drawer.isOpen,
  list: sortBy(state.common.usersList.list, ['first_name', 'last_name']),
  selectedId: state.common.usersList.selectedId,
  userId: state.common.user.id,
  token: state.common.user.token
});

const mapDispatchToProps = {
  fetchUsersList,
  selectAllUserIdForReports,
  selectUserInUserlist,
  setDrawerStatus
};

interface IProps {
  fetchUsersList: (token: string) => Promise<any>;
  selectUserInUserlist: (userId: number) => void;
  userId: number;
  token: string;
  selectAllUserIdForReports: () => void;
}

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),
  withHandlers({
    /* eslint-disable no-shadow */
    handleUserlistItemClick: ({setDrawerStatus, selectUserInUserlist}) => (
      id
    ) => () => {
      selectUserInUserlist(id);
      setDrawerStatus();
    }
  }),

  lifecycle<IProps, {}>({
    componentDidMount() {
      const {
        userId,
        token,
        fetchUsersList,
        selectAllUserIdForReports,
        selectUserInUserlist
      } = this.props;

      fetchUsersList(token).then(() => {
        selectUserInUserlist(userId);
        selectAllUserIdForReports();
      });
    }
  })
)(Drawer);
