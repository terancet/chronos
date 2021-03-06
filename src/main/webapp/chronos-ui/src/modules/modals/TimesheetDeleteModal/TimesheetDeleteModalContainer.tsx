import {connect} from 'react-redux';
import {compose, withHandlers, withState} from 'recompose';
import {removeCurrentModal} from 'modules/modals/actions/modalsActions';
import TimesheetDeleteModal from './TimesheetDeleteModal';
import {deleteRecordApi} from '../../modals/actions/api/deleteRecordApi';

const mapStateToProps = (state) => ({
  selectedId: state.timesheet.selectedId,
  token: state.common.user.token
});

const mapDispatchToProps = {
  deleteRecordApi,
  removeCurrentModal
};

export default compose(
  connect(
    mapStateToProps,
    mapDispatchToProps
  ),
  withState('isOpen', 'setIsOpen', true),

  /* eslint-disable no-shadow */
  withHandlers({
    handleOnClose: ({isOpen, setIsOpen, removeCurrentModal}) => () => {
      if (isOpen) {
        setIsOpen(false);
        removeCurrentModal();
      }
    }
  }),
  withHandlers({
    handleDeleteItem: ({
      deleteRecordApi,
      handleOnClose,
      selectedId,
      token
    }) => () => {
      deleteRecordApi(selectedId, token)
        .catch(() => {})
        .finally(handleOnClose());
    }
  })
)(TimesheetDeleteModal);
