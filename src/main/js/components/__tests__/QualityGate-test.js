import React from 'react';
import {shallow} from 'enzyme';
import QualityGate from '../QualityGate';

it ('QualityGate-OK', () => {
  expect(
    shallow(
      <QualityGate
       qg_status={'OK'}
      />

    )
  ).toMatchSnapshot();
});

it ('QualityGate-WARNING', () => {
  expect(
    shallow(
      <QualityGate
       qg_status={'WARN'}
      />

    )
  ).toMatchSnapshot();
});

it ('QualityGate-KO', () => {
  expect(
    shallow(
      <QualityGate
       qg_status={'KO'}
      />

    )
  ).toMatchSnapshot();
});
