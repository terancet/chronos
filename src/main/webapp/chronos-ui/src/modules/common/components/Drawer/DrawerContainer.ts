import {connect} from 'react-redux';
import {compose, lifecycle, withHandlers} from 'recompose';
import sortBy from 'lodash/sortBy';

import {fetchUsersList} from '../../actions/api/fetchUsersList';
import {selectUserInUserlist, setDrawerStatus} from '../../actions/drawer';
import Drawer from './Drawer';

const mapStateToProps = (state) => ({
  isOpen: state.common.drawer.isOpen,
  list: sortBy(state.common.users.list, ['first_name', 'last_name']),
  selectedId: state.common.users.selectedId,
  userId: state.auth.signIn.user.id
});

const mapDispatchToProps = {
  fetchUsersList,
  selectUserInUserlist,
  setDrawerStatus
};

interface IProps {
  fetchUsersList: () => Promise<any>;
  selectUserInUserlist: (userId: number) => void;
  userId: number;
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
      const {fetchUsersList, selectUserInUserlist, userId} = this.props;

      fetchUsersList().then(() => selectUserInUserlist(userId));
    }
  })
)(Drawer);
