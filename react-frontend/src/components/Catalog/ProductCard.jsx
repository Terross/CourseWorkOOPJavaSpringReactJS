import {Button, Container, Paper} from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
const useStyles = makeStyles((theme) => ({
    root: {
      display: 'flex',
      flexWrap: 'wrap',
      '& > *': {
        margin: theme.spacing(1),
        width: theme.spacing(24),
        height: theme.spacing(24),
      },
    },
  }));
export default function ProductCard(props) {
    const classes = useStyles();
    const addToCart = (product) => {
        props.setOrderState([...props.orderState,product]);
    }
    const removeToCart = (product) => {
      let index = props.orderState.indexOf(product);
     
      props.orderState.splice(index,1)
      props.setOrderState([...props.orderState]);
    }
    return(
        <div className={classes.root}>
            <Paper elevation={3}>
            <Container align="center" style={{height: "50px"}}>
            <h2>{props.product.name}</h2>
            </Container>
            <Container align="center">
            <h3 >{props.product.price + "$"}</h3>
            </Container>
            <Container >
              
                <Button variant="contained" color="primary" style={{width:"45%",
                                                                    margin:"3px",
                                                                    verticalAlign:"bottom"}}
                                                                    onClick={() =>addToCart(props.product)}>
                  Add
                </Button>
                <Button variant="contained" color="primary" style={{width:"45%",
                                                                    margin:"3px",
                                                                    verticalAlign:"bottom"
                                                                    }}
                                                                    onClick={() => removeToCart(props.product)}>
                  Remove
                </Button>
              
              
            </Container>
            
            </Paper>
        </div>
        
    );
}