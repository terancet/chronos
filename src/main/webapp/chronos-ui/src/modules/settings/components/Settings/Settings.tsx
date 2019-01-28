import * as React from 'react';
import {
  Button,
  FormControl,
  IconButton,
  InputLabel,
  MenuItem,
  OutlinedInput,
  Select,
  TextField,
  Typography,
  withStyles,
  WithStyles
} from '@material-ui/core';

import AddIcon from '@material-ui/icons/Add';
import RemoveCircleOutlineIcon from '@material-ui/icons/RemoveCircleOutline';
import DoneIcon from '@material-ui/icons/Done';
import {IListItem} from '../../../modals/reducers/projects';
import {COLORS} from '../../../../shared/constatns';
import * as theme from './Settings.scss';
import styles from './styles';

interface IProps extends WithStyles<typeof styles> {
  color: {id: string};
  projects: IListItem[];
  projectsList: IListItem[];
  isBtnActive: boolean;
  isLoading: boolean;
  shouldBlockNavigation: boolean;

  handleAddProjectBtnClick(): void;
  handleColorChange: (id: number) => (id: React.SyntheticEvent) => void;
  handleDeleteProject: (id: number) => (id: React.SyntheticEvent) => void;
  handleFormSubmit(): void;
  handleProjectChange: (id: number) => (id: React.SyntheticEvent) => void;
}

const Settings: React.FunctionComponent<IProps> = ({
  classes,
  projects,
  color,
  isBtnActive,
  handleAddProjectBtnClick,
  handleColorChange,
  handleDeleteProject,
  handleFormSubmit,
  handleProjectChange
}) => (
  <div>
    <Typography variant="h6" color="inherit" className={classes.navTitle}>
      Projects:
    </Typography>

    <form onSubmit={handleFormSubmit} className={classes.formWrapper}>
      {projects.map((project) => (
        <div key={project.id} className={theme.formControlWrapper}>
          <IconButton
            color="primary"
            aria-label="Menu"
            onClick={handleDeleteProject(project.id)}
            className={classes.deleteBtn}
          >
            <RemoveCircleOutlineIcon />
          </IconButton>
          <FormControl variant="outlined" className={classes.formControl}>
            <TextField
              id="project"
              name={`${project.project_name}`}
              label="Project"
              margin="normal"
              variant="outlined"
              type="string"
              defaultValue={project.project_name}
              className={classes.inputText}
              InputProps={{
                classes: {
                  notchedOutline: classes.textFieldFocusedNotchedOutline
                }
              }}
              onChange={handleProjectChange(project.id)}
            />
          </FormControl>
          <FormControl className={classes.formControlSelect}>
            <InputLabel htmlFor="color" className={classes.labelText}>
              Color
            </InputLabel>
            <Select
              value={project.id}
              onChange={handleColorChange(project.id)}
              className={classes.selectColor}
              renderValue={() => (
                <div>
                  <div
                    className={theme.colorOption}
                    style={{
                      backgroundColor: `${color[`${project.id}`]}`
                    }}
                  />
                </div>
              )}
              input={
                <OutlinedInput
                  labelWidth={50}
                  name={`${project.id}`}
                  id="color"
                  classes={{
                    notchedOutline: classes.textFieldFocusedNotchedOutline
                  }}
                />
              }
            >
              {COLORS.map((item) => (
                <MenuItem
                  key={Math.random()}
                  value={item}
                  className={classes.selectItemColor}
                >
                  <div
                    className={theme.colorOption}
                    style={{
                      backgroundColor: `${item}`
                    }}
                  />
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </div>
      ))}
      <div className={theme.btnWrapper}>
        <Button
          type="button"
          color="primary"
          className={classes.controlBtn}
          onClick={handleAddProjectBtnClick}
        >
          Add new project
          <AddIcon className={classes.rightIcon} />
        </Button>
        <Button
          type="submit"
          color="primary"
          disabled={!isBtnActive}
          className={`${classes.controlBtn} ${
            isBtnActive ? classes.saveBtnActive : classes.saveBtnDisabled
          } ${classes.controlBtnSave}`}
        >
          Save
        </Button>
      </div>
    </form>
  </div>
);

export default withStyles(styles)(Settings);
